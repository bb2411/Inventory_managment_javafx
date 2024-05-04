package com.example.demo2;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DatabaseController {
    static {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Error loading MySQL JDBC driver: " + e.getMessage());
        }
    }
    private static final String url = "jdbc:mysql://localhost:3306/javapractice";
    private static final String user = "root";
    private static final String pass = "root";
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            Alert a=new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Failed To Connect Database");
            a.setContentText(e.getMessage());
            a.showAndWait();
            throw new SQLException("not connected");
        }
    }
}
