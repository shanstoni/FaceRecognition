package com.company;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    }

    public static void main(String[] args) {

        DBConnection dbConnection = new DBConnection();
        dbConnection.setConnection();

        ModelPerson modelPerson = new ModelPerson("Aaa", "Bbb", "Ccc");
        ControlPerson controlPerson = new ControlPerson();
        controlPerson.insertDataToDB(modelPerson);

        try {
            dbConnection.executeSQL("SELECT * from person where ID=4");
            dbConnection.resultSet.first();
            System.out.println(dbConnection.resultSet.getString("first_name"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame jFrame = new JFrame("loginpanel");
        jFrame.setContentPane(new loginpanel().panel1);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
