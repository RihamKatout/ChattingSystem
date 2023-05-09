package com.client.p2pclient;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javax.swing.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.Set;


public class GUIController implements Initializable {
    @FXML
    private TextField LocalIP, LocalPort, RemoteIP, RemotePort, ServerIP, ServerPort, passwordTextBox, usernameTestBox, Status;
    @FXML
    private Button loginButton, logoutButton, connectButton, sendButton, updateButton;
    @FXML
    private TextArea messageBox;
    @FXML
    private VBox messagesArea;
    public static VBox messagesArea2;
    @FXML
    private VBox onlineArea;
    public static VBox onlineArea2;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messagesArea2 = messagesArea;
        onlineArea2 = onlineArea;
    }
    private void loginEnable(){
        usernameTestBox.setDisable(true);
        passwordTextBox.setDisable(true);
        loginButton.setDisable(true);
        logoutButton.setDisable(false);
        ServerIP.setDisable(true);
        ServerPort.setDisable(true);
        RemoteIP.setDisable(false);
        RemotePort.setDisable(false);
        connectButton.setDisable(false);
        sendButton.setDisable(false);
        messageBox.setDisable(false);
        updateButton.setDisable(false);
    }
    private void logoutEnable(){
        usernameTestBox.setDisable(false);
        passwordTextBox.setDisable(false);
        loginButton.setDisable(false);
        logoutButton.setDisable(true);
        ServerIP.setDisable(false);
        ServerPort.setDisable(false);
        RemoteIP.setDisable(true);
        RemotePort.setDisable(true);
        connectButton.setDisable(true);
        sendButton.setDisable(true);
        messageBox.setDisable(true);
        updateButton.setDisable(true);
    }
    private void clearAll(){
        messageBox.setText("");
        usernameTestBox.setText("");
        passwordTextBox.setText("");
        ServerIP.setText("");
        ServerPort.setText("");
        LocalIP.setText("");
        LocalPort.setText("");
        RemoteIP.setText("");
        RemotePort.setText("");
        Status.setText("");
    }
    @FXML
    void login(ActionEvent event) throws IOException {
        String name = usernameTestBox.getText(), password = passwordTextBox.getText(), serverIP = ServerIP.getText(), hostname = "";
        int serverPort = Integer.parseInt(ServerPort.getText());
        try {
            hostname = InetAddress.getLocalHost().getHostAddress();
            MainClass.mainUser.setIP(hostname);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        MainClass.mainUser.setTCPServerIP(serverIP);
        MainClass.mainUser.setTCPServerPort(serverPort);
        MainClass.mainUser.setUsername(name);
        passwordTextBox.setText("");
        MainClass.mainUser.createUDPThread();
        String msg = "login%" + name + "%" + password + "%" + hostname + "%" + MainClass.mainUser.getUDPServer().getPort();
        String response = MainClass.helper.sendToServer(serverIP, serverPort, msg);
        if(response.equals("failed")){
            JOptionPane.showMessageDialog(new JFrame(),"Wrong username or password","Alert",JOptionPane.ERROR_MESSAGE);
        }
        else{
            MainClass.mainUser.setTCPServerIP(serverIP);
            MainClass.mainUser.setTCPServerPort(serverPort);
            MainClass.mainUser.setTCPServerIP(serverIP);
//            MainClass.mainUser.createUDPThread();
            Status.setText("Logged in successfully.");
            LocalIP.setText(hostname);
            LocalPort.setText(String.valueOf(MainClass.mainUser.getPort()));
            loginEnable();
        }
    }
    @FXML
    void logout(ActionEvent event) throws IOException {
        String name = usernameTestBox.getText();
        String serverIP = ServerIP.getText();
        int serverPort = Integer.parseInt(ServerPort.getText());
        String msg = "logout%" + name + "%" + MainClass.mainUser.getIP() + "%" + MainClass.mainUser.getPort();
        MainClass.helper.sendToServer(serverIP, serverPort, msg);
        messagesArea2.getChildren().clear();
        messagesArea.getChildren().clear();
        logoutEnable();
        clearAll();
    }
    @FXML
    void onSendButtonClick(ActionEvent eve) throws IOException {
        UDPClientThread.setFriendIP(RemoteIP.getText());
        UDPClientThread.setFriendPort(Integer.parseInt(RemotePort.getText()));
        UDPClientThread.setSentData(messageBox.getText());
        UDPClientThread.sendData();
        Node label = new Label("Me: "+messageBox.getText());
        label.setStyle("-fx-text-fill: red;");
        label.setOnMouseClicked(event->selectNode(label));
        messagesArea.getChildren().add(label);
    }
    @FXML
    void onSendButtonClick() throws IOException {
        UDPClientThread.setFriendIP(RemoteIP.getText());
        UDPClientThread.setFriendPort(Integer.parseInt(RemotePort.getText()));
        UDPClientThread.setSentData(messageBox.getText());
        UDPClientThread.sendData();
        Node label = new Label("Me3: " + messageBox.getText());
        label.setOnMouseClicked(event -> selectNode(label));
        messagesArea.getChildren().add(label);

    }
    private static void selectNode(Node node) {
        deselectAll();
        node.setStyle("-fx-background-color: blue;");
    }
    private static void deselectAll() {
        for (var child : messagesArea2.getChildren()) {
            if (child instanceof Label) {
                ((Label) child).setStyle("-fx-background-color: transparent;");
            }
        }
    }
    public static void receivedShow(String s){
        Platform.runLater(() -> {
            Node label = new Label(s);
            label.setStyle("-fx-text-fill: green;");
            label.setOnMouseClicked(event->selectNode(label));
            messagesArea2.getChildren().add(label);
        });
    }
    @FXML
    void updatePort(ActionEvent e) throws IOException {
        MainClass.mainUser.createUDPThread();
        LocalPort.setText(String.valueOf(MainClass.mainUser.getPort()));
        Status.setText("Port updated successfully.");
    }
    public static void onlineUpdate(String s){
        onlineArea2.getChildren().removeAll();
        Platform.runLater(() -> {
            String data[] = s.split("%");
            for(int i =0 ;i< data.length ; i ++ ){
                String data2[] = data[i].split(",");
                onlineArea2.getChildren().add(new Label(data[i]));
            }
        });
    }

    @FXML
    void onSendAllButtonClick(ActionEvent e){

    }
    @FXML
    void connectServerAndDest(ActionEvent event) throws IOException, InterruptedException {
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
        //MainClass.helper.setServerIP(ServerIP.getText());
        //MainClass.helper.setServerPort(Integer.parseInt(ServerPort.getText()));
        UDPServer.setPort(Integer.parseInt(LocalPort.getText()));
        UDPClientThread.setFriendIP(RemoteIP.getText());
        UDPClientThread.setFriendPort(Integer.parseInt(RemotePort.getText()));
        Thread threadServer = new Thread(UDPServer,"serverThread");
        threadServer.start();
    }
}
