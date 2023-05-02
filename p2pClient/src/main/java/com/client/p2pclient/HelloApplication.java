package com.client.p2pclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("messenger.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setTitle("messenger");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
        //test
    }
    private static String reader(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        String response = new String(buffer, 0, bytesRead);
        return response;
    }
    public static void main(String[] args) throws IOException {

        launch();
//        String sentenceFromClient;
//        String responseFromClient;
//        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        Socket clientSocket = new Socket("192.168.1.10", 6789);
        OutputStream outputStream = clientSocket.getOutputStream();
        InputStream inputStream = clientSocket.getInputStream();
        String message = "amjad";
        outputStream.write(message.getBytes());
        System.out.println("Server response: " + reader(inputStream));
        clientSocket.close();
    }

}