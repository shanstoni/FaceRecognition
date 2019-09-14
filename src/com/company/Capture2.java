package com.company;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_face.FaceRecognizer;
import org.bytedeco.opencv.opencv_face.LBPHFaceRecognizer;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.IntBuffer;

import static org.bytedeco.opencv.global.opencv_core.CV_32SC1;
import static org.bytedeco.opencv.global.opencv_imgcodecs.*;
import static org.bytedeco.opencv.global.opencv_imgproc.*;

public class Capture2 {

    public final static String PHOTOS_PATH = "C:/photos";
    public final static String HAAR_XML = "C:/photos/haarcascade_frontalface_alt";

    JFrame jFrame = new JFrame();
    private JLabel label_photo;
    private JButton saveButton;
    private JLabel counterLabel;
    private JPanel capture2panel;

    public Capture2() {

        jFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        jFrame.getContentPane().add(capture2panel);
        jFrame.revalidate();
        jFrame.pack();
        jFrame.setVisible(true);



        startCamera();
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                startCamera();
            }
        });
    }

    private Capture2.DaemonThread myThread = null;



    private VideoCapture webSource = null;
   // org.opencv.core.Mat cameraImg = new org.opencv.core.Mat();
    Mat cameraImage = new Mat();
    CascadeClassifier cascade = new CascadeClassifier(HAAR_XML);
    BytePointer mem = new BytePointer();
    RectVector detectedFaces = new RectVector();

    String root, firstNamePerson, lastNamePerson, officePerson;
    int numSamples = 25, sample = 1, idPerson;

    DBConnection dbConnection = new DBConnection();

    public class DaemonThread implements Runnable {

        protected volatile boolean runnable = false;

        @Override
        public void run() {
            synchronized (this) {
                while (runnable) {
                    try {
                        if (webSource.grab()) {
                            webSource.retrieve(cameraImage);
                            Graphics g = label_photo.getGraphics();
                            Mat imageColor = new Mat();
                            imageColor = cameraImage;

                            Mat imageGray = new Mat();

                            cvtColor(imageColor, imageGray, COLOR_BGRA2GRAY);

                            RectVector detectedFaces = new RectVector();
                            cascade.detectMultiScale(imageColor, detectedFaces, 1.1, 1, 0, new Size(150, 150), new Size(500, 500));

                            for (int i = 0; i < detectedFaces.size(); i++) {
                                Rect dadosFace = detectedFaces.get(0);
                                rectangle(imageColor, dadosFace, new Scalar(255, 255, 255, 5));
                                Mat face = new Mat(imageGray, dadosFace);
                                opencv_imgproc.resize(face, face, new Size(160, 160));

                                if (saveButton.getModel().isPressed()) {

                                    if (sample <= numSamples) {
                                        String cropped = "C:\\photos\\person." + "Blazej" + "." + sample + ".jpg";
                                        imwrite(cropped, face);

                                        //System.out.println("Foto " + amostra + " capturada\n");
                                        counterLabel.setText(String.valueOf(sample));
                                        sample++;
                                    }
                                    if (sample > 25) {
                                        generate();
                                        insertDatabase();
                                        System.out.println("File Generated");
                                        stopCamera();
                                    }
                                }
                            }

                            imencode(".bmp", cameraImage, mem);
                            Image im = ImageIO.read(new ByteArrayInputStream(mem.getStringBytes()));
                            BufferedImage buff = (BufferedImage) im;

                            if (g.drawImage(buff, 0, 0, getWidth(), getHeight() - 90, 0, 0, buff.getWidth(), buff.getHeight(), null)) {
                                if (runnable == false) {
                                    System.out.println("Salve a Foto");
                                    this.wait();
                                }
                            }
                        }

                    } catch (IOException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Erro ao iniciar camera (IOEx)\n" + ex);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Erro ao iniciar camera (Interrupted)\n" + ex);
                    }
                }
            }
        }


    }

    public void generate() {
        File directory = new File(PHOTOS_PATH);
        FilenameFilter filenameFilter = new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".jpg") || name.endsWith(".png");
            }
        };

        File[] files = directory.listFiles(filenameFilter);
        MatVector photos = new MatVector(files.length);
        Mat labels = new Mat(files.length, 1, CV_32SC1);
        IntBuffer labelsBuffer = labels.createBuffer();

        int counter = 0;
        for(File image : files){
           Mat photo = imread(image.getAbsolutePath(), IMREAD_GRAYSCALE);
           int idPerson = 1;
           opencv_imgproc.resize(photo, photo, new Size(160, 160));

           photos.put(counter, photo);
           labelsBuffer.put(counter, idPerson);
           counter++;
        }

        FaceRecognizer lbph = LBPHFaceRecognizer.create();
        lbph.train(photos, labels);
        lbph.save(PHOTOS_PATH + "/calssifierLBPH.yml");

    }

    public void insertDatabase() {
        DBConnection dbConnection = new DBConnection();
        dbConnection.setConnection();
        String milis = Long.toString(System.currentTimeMillis());
        ModelPerson modelPerson = new ModelPerson("Aaa" +  milis , "Bbb", "Ccc");
        ControlPerson controlPerson = new ControlPerson();
        controlPerson.insertDataToDB(modelPerson);
        dbConnection.disconnection();
    }

    public void stopCamera() {
        myThread.runnable = false;
        webSource.release();
        jFrame.dispose();
    }

    public void startCamera() {
        webSource = new VideoCapture(0);
        myThread = new Capture2.DaemonThread();
        Thread thread = new Thread(myThread);
        thread.setDaemon(true);
        myThread.runnable = true;
        thread.start();

    }

    public int getWidth() {
        return 300;
    }

    public int getHeight() {
        return 300;
    }
}
