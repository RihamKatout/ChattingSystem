package com.client.p2pclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class MainClass extends Application  {
    public static Helper helper;
    public static User mainUser ;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource("GUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 450);
        stage.setTitle("login");
        stage.setScene(scene);
        stage.show();
//        test
    }

    public static void main(String[] args) throws IOException {
        helper = new Helper();
        mainUser = new User();
//        Thread UDPThread = new Thread(new UDPServerThread());
//        UDPThread.start();
        launch();
        System.exit(0);
    }

}