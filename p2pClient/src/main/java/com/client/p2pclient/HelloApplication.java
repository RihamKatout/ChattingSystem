package com.client.p2pclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloApplication extends Application  {
    public static ServerHelper serverHelper;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("GUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setTitle("messenger");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        //test
    }

    public static void main(String[] args) throws IOException {

        launch();
//        String sentenceFromClient;
//        String responseFromClient;
//        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        onlineStatusThread thread = new onlineStatusThread();
        Thread onlineThread = new Thread(thread);
        onlineThread.start();
        serverHelper = new ServerHelper();
        serverHelper.sendToServer(Request.LOG_IN,"amjadtest");
        serverHelper.sendToServer(Request.LOG_IN,"amjadmain");
//        System.out.println("test1");
        System.out.println("test2");
        launch();
        onlineThread.interrupt();
        System.exit(0);
    }

}