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
        stage.setTitle("Client");
        stage.setMaximized(true);
        stage.setScene(scene);
        stage.show();
//        test
    }

    public static void main(String[] args) throws IOException {
        helper = new Helper();
//        mainUser =new User();
//        Thread UDPThread = new Thread(new UDPServerThread());
//        UDPThread.start();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    String name = mainUser.getUsername();
                    String serverIP = mainUser.getTCPServerIP();
                    int serverPort = mainUser.getTCPServerPort();
                    String msg = "logout%" + name + "%" + MainClass.mainUser.getIP();
                    MainClass.helper.sendToServer(serverIP, serverPort, msg);
                } catch (IOException e) {
                    System.out.println("User Not init");
                }
            }
        });
        launch();
        System.exit(0);
    }

}