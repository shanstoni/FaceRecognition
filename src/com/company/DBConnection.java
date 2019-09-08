package com.company;

import java.sql.*;

public class DBConnection {

    public Statement statement;
    public ResultSet resultSet;

    public Connection connection;

    private String driver = "org.mysql.Driver";
    private String root = "jdbc:mysql://127.0.0.1/facial_recognition";
    private String user = "root";
    private String pass = "";


    public void setConnection(){
        try {
            System.setProperty("jdbc.Driver", driver);
            connection = DriverManager.getConnection(root, user, pass);
            System.out.println("OK");
        } catch (SQLException e) {
            System.err.println("setConnection ERROR: " + e.toString());
        }
    }

    public void disconnection(){
        try {
           connection.close();
        } catch (Exception e) {
            System.err.println("disconnection ERROR: " + e.toString());
        }
    }

    public void executeSQL(String SQL){
        try {
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            resultSet = statement.executeQuery(SQL);
        } catch (Exception e) {
            System.err.println("executeSQL ERROR: " + e.toString());
        }
    }



/*  public static void main(String[] args) {
        DBConnection dbConnection = new DBConnection();
        dbConnection.setConnection();
    }*/
}
