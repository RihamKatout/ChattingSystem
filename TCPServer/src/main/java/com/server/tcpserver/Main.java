package com.server.tcpserver;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
//        OnlineStatus.newOnlineUser(new String( "amjadmain, ,192.168.1.10,6789"));
//        OnlineStatus.newOnlineUser(new String( "amjadtest, ,192.168.1.25,6789"));
//        OnlineStatus.updateClients();
        LoginThread loginThread = new LoginThread();
        Thread threadLogin = new Thread(loginThread);
        threadLogin.start();
        launch();
        threadLogin.interrupt();
        System.exit(0);
    }


}