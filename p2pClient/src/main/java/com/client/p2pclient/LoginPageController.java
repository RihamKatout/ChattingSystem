package com.client.p2pclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class LoginPageController {

    @FXML
    private Button loginButton;
    @FXML
    private Button CreateUserButton;

    @FXML
    private Label UserNotFoundLabel;
    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField userNameTextField;
    @FXML
    void CreateUser(ActionEvent event) throws IOException {
        String name = '&'+userNameTextField.getText();
        String pass = passwordTextField.getText();
        String serverResponse = MainClass.helper.sendToLoginServer(name,pass);
    }
    @FXML
    void login(ActionEvent event) throws IOException {
        MainClass.mainUser = new User();
        onlineStatusThread thread = new onlineStatusThread();
        thread.setWelcomeSocket();
        Thread onlineThread = new Thread(thread);
        onlineThread.start();
        MainClass.helper = new Helper();//172.19.203.142
        String name = userNameTextField.getText();
        String pass = passwordTextField.getText();
        MainClass.helper.setServerIp("192.168.1.9");
        MainClass.helper.setServerPort(1218);
//
//        InetAddress ip;
//        String clientIp;
//        try {
//            ip = InetAddress.getLocalHost();
//            clientIp = ip.getHostAddress();
//        } catch (UnknownHostException e) {
//            e.printStackTrace();
//        }
//        int onlinePort = thread.getWelcomeSocket().getLocalPort();
////        String serverResponse = MainClass.helper.sendToLoginServer(name,pass,clientIp,onlinePort,clientPort); //"192.168.1.9",1218
//        if(serverResponse.equals("failed")){
//            System.out.println("Failed to login");
//            UserNotFoundLabel.setVisible(true);
//            CreateUserButton.setVisible(true);
//            return ;
//        }
//        System.out.println("logged in successfully " + serverResponse);
//        String userData[] = serverResponse.split(",");
//        MainClass.mainUser.setUsername(userData[0]);
//        MainClass.mainUser.setIP(userData[2]);
//        MainClass.mainUser.setPort(Integer.parseInt(userData[3]));
//        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource("loginPage.fxml"));
////        FXMLLoader fxmlLoader = new FXMLLoader(MainClass.class.getResource("loginPage.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 300, 450);
//        Stage stage = new Stage();
//        stage.setTitle("login");
//        stage.setScene(scene);
//        stage.setMaximized(true);
//        stage.show();
    }

}
