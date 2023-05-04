package com.client.p2pclient;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class MainClass extends Application  {
    public static ServerHelper serverHelper;
    public static User mainUser ;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource("GUI.fxml"));
//        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource("loginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setTitle("messenger");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
//        test
    }

    public static void main(String[] args) throws IOException {
        UDPServerThread UDPServer = new UDPServerThread();
        Thread threadServer = new Thread(UDPServer);
        threadServer.start();
//        launch();
//        String sentenceFromClient;
//        String responseFromClient;
//        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        mainUser = new User();
        onlineStatusThread thread = new onlineStatusThread();
        Thread onlineThread = new Thread(thread);
        onlineThread.start();
        serverHelper = new ServerHelper();//172.19.203.142
//        serverHelper.sendToServer(Request.LOG_IN,"amjadtest");
//        serverHelper.sendToServer(Request.LOG_IN,"amjadmain");
//        System.out.println("test1");
//        System.out.println("test2");
        launch();
        onlineThread.interrupt();
        System.exit(0);
    }

}