package com.client.p2pclient;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;


public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() throws IOException {
        welcomeText.setText("Welcome to JavaFX Application!");
//        HelloApplication.serverHelper.sendToServer(Request.LOG_IN,"hello");

    }
}