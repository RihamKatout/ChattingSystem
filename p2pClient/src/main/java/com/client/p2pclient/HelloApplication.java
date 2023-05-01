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

public class HelloApplication extends Application implements Initializable {
    public static ServerHelper serverHelper;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        //test
    }

    public static void main(String[] args) throws IOException {

//        String sentenceFromClient;
//        String responseFromClient;
//        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

      serverHelper.sendToServer(Request.MESSAGE_SENT,"amjad");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            serverHelper = new ServerHelper() ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}