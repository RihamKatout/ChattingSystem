package com.client.p2pclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginPageController {

    @FXML
    private Button loginButton;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField userNameTextField;

    @FXML
    void login(ActionEvent event) throws IOException {
        String name = userNameTextField.getText();
        String pass = passwordTextField.getText();
        String serverResponse = MainClass.serverHelper.sendToLoginServer(name,pass); //"192.168.1.9",1218
        if(serverResponse.equals("failed")){
            System.out.println("Failed to login");
            return ;
        }
        System.out.println("logged in successfully " + serverResponse);
        String userData[] = serverResponse.split(",");
        MainClass.mainUser.setUsername(userData[0]);
        MainClass.mainUser.setIP(userData[2]);
        MainClass.mainUser.setPort(Integer.parseInt(userData[3]));
    }

}
