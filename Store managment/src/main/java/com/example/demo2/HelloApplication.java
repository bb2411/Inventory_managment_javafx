package com.example.demo2;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class HelloApplication extends Application {
    public static Connection conn;
    private static Statement stat;
    {
        try{
            HelloApplication.conn=DatabaseController.getConnection();
        }catch (SQLException e){
            Alert a=new Alert(Alert.AlertType.INFORMATION);
            a.setHeaderText("Error");
            a.setContentText(e.getMessage());
            a.showAndWait();
        }
    }
    @Override
    public void start(Stage stage) throws IOException, SQLException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 700);
        stage.setTitle("Store Managment System");
        File iconfile=new File("D:\\Programs\\java\\javafx\\Store managment\\target\\LOGO_CUSP-removebg-preview.png");
        String path=iconfile.toURI().toString();
        Image icon=new Image(path);
        stage.getIcons().add(icon);
        stage.setScene(scene);
        stage.isFullScreen();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}