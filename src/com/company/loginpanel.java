package com.company;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.RectVector;
import org.bytedeco.opencv.opencv_objdetect.CascadeClassifier;
import org.opencv.core.Core;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.company.Capture2.HAAR_XML;

public class loginpanel {
    private JPanel panel1;
    private JButton captureButton;
    private JButton dataButton;
    private JButton recognizeButton;

    public loginpanel() {
        captureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        recognizeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        dataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        captureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Capture2 capture2 = new Capture2();
                //capture.setVisible(true);
            }
        });
    }

    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);


//        DBConnection dbConnection = new DBConnection();
//        dbConnection.setConnection();
//        ModelPerson modelPerson = new ModelPerson("Aaa", "Bbb", "Ccc");
//        ControlPerson controlPerson = new ControlPerson();
//        controlPerson.insertDataToDB(modelPerson);
//        dbConnection.disconnection();

//        try {
//            dbConnection.executeSQL("SELECT * from person where ID=4");
//            dbConnection.resultSet.first();
//            System.out.println(dbConnection.resultSet.getString("first_name"));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        JFrame jFrame = new JFrame("loginpanel");
        jFrame.setContentPane(new loginpanel().panel1);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);


//        Mat cameraImage = new Mat();
//        CascadeClassifier cascade = new CascadeClassifier(HAAR_XML);
//        BytePointer mem = new BytePointer();
//        RectVector detectedFaces = new RectVector();
    }
}
