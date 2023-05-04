package com.client.p2pclient;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class GUIController {

    @FXML
    private TextField LocalIP, LocalPort, RemoteIP, RemotePort, ServerIP, ServerPort;
    @FXML
    private TextArea messageBox;

    @FXML
    void onSendButtonClick(ActionEvent event) throws IOException {
        //prepare IPs, ports & sendData
        UDPServerThread.setIP(InetAddress.getByName(LocalIP.getText()));
        UDPServerThread.setPort(Integer.parseInt(LocalPort.getText()));
        UDPClientThread.setFriendIP(InetAddress.getByName(RemoteIP.getText()));
        UDPClientThread.setFriendPort(Integer.parseInt(RemotePort.getText()));
        UDPClientThread.setSentData(messageBox.getText().getBytes());
        UDPClientThread.sendData();
    }
}
