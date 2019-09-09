package com.company;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.*;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.bytedeco.opencv.opencv_videoio.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.bytedeco.opencv.global.opencv_imgcodecs.imencode;
import static org.bytedeco.opencv.global.opencv_imgcodecs.imwrite;
import static org.bytedeco.opencv.global.opencv_imgproc.*;


public class Capture2 {

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
    }

    private Capture2.DaemonThread myThread = null;


    VideoCapture webSource = null;
    Mat cameraImage = new Mat();
    CascadeClassifier cascade = new CascadeClassifier("");
    BytePointer mem = new BytePointer();
    RectVector detectedFaces = new RectVector();

    String root;
    int numSamples = 25;
    int sample = 1;

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
    }

    public void insertDatabase() {
    }

    public void stopCamera() {
        myThread.runnable = false;
        webSource.release();
       // JFrame.EXIT_ON_CLOSE;
    }

    public void startCamera() {


    }

    public int getWidth() {
        return 300;
    }

    public int getHeight() {
        return 300;
    }
}
