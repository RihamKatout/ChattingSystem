
package com.client.p2pclient;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.Set;

public class GUIController implements Initializable {

    @FXML
    private TextField LocalIP;

    @FXML
    private TextField LocalPort;

    @FXML
    private TextField RemoteIP;

    @FXML
    private TextField RemotePort;

    @FXML
    private TextField ServerIP;

    @FXML
    private TextField ServerPort;

    @FXML
    private TextArea messageBox;
    @FXML
    private VBox messagesArea;
    public static VBox messagesArea2;
    @FXML
    private Button testConnectionButton;

    @FXML
    void connectServerAndDest(ActionEvent event) throws UnknownHostException, SocketException, InterruptedException {
//        MainClass.threadServer.interrupt();
//        MainClass.threadServer.join();
        Set<Thread> setOfThread = Thread.getAllStackTraces().keySet();
        //Iterate over set to find yours
        for(Thread thread : setOfThread){
            if(thread.getName().equals("serverThread")){
                System.out.println("hello");
                thread.interrupt();
            }
        }
        UDPServerThread UDPServer = new UDPServerThread();
        MainClass.serverHelper.setServerIp(ServerIP.getText());
        MainClass.serverHelper.setServerPort(Integer.parseInt(ServerPort.getText()));
        UDPServer.setPort(Integer.parseInt(LocalPort.getText()));
        UDPClientThread.setFriendIP(InetAddress.getByName(RemoteIP.getText()));
        UDPClientThread.setFriendPort(Integer.parseInt(RemotePort.getText()));
        Thread threadServer = new Thread(UDPServer,"serverThread");
        threadServer.start();
    }

    @FXML
    void onSendButtonClick(ActionEvent event) throws IOException {
//        prepare IPs, ports & sendData
        UDPClientThread.setSentData(messageBox.getText().getBytes());
        UDPClientThread.sendData();
        messagesArea.getChildren().add(new Label("Me : " + messageBox.getText()));
    }

    public static void receivedShow(String s){
        Platform.runLater(() -> {
        // Update UI components here
            messagesArea2.getChildren().add(new Label("received : " + s));
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messagesArea2 = messagesArea;
    }
}
