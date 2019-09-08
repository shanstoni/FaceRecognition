package com.company;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ControlPerson {

    DBConnection dbConnection = new DBConnection();

    public void insertDataToDB(ModelPerson modelPerson){

        try {
            dbConnection.setConnection();
            PreparedStatement preparedStatement = dbConnection.connection.prepareStatement("insert into person (first_name, last_name, office) values (?, ?, ?)");
            preparedStatement.setString(1,modelPerson.getFirst_name());
            preparedStatement.setString(2,modelPerson.getLast_name());
            preparedStatement.setString(3,modelPerson.getNotes());
            preparedStatement.executeUpdate();
            dbConnection.disconnection();
        } catch (SQLException e) {
            System.err.println("insert ERROR: " + e.toString());
        }

    }

    public void selectDataFromDB(int id){

    }

}
