package com.server.tcpserver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main extends Application  {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
    public  static void main(String[] args) throws IOException, InterruptedException {
        InetAddress ip;
        String hostname;
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostAddress();
            System.out.println("Your current IP address : " + hostname);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        LoginThread loginThread = new LoginThread();
        Thread threadLogin = new Thread(loginThread);
        threadLogin.start();
        launch();
        threadLogin.interrupt();
        System.exit(0);
    }
}