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
import javafx.util.Pair;

import javax.swing.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.*;


public class GUIController implements Initializable {
    @FXML
    private TextField LocalIP, LocalPort, RemoteIP, RemotePort, ServerIP, ServerPort, passwordTextBox, usernameTestBox, Status;
    @FXML
    private Button loginButton, logoutButton, connectButton, sendButton, updateButton,sendAllButton, DeleteMessegeButton,DeleteAllButton;
    @FXML
    private TextArea messageBox;
    @FXML
    private VBox messagesArea;

    private static Map<String, Pair<String, String>> map = new HashMap<>();

    public static VBox messagesArea2;

    private static Node selectedNode ;
    @FXML
    private VBox onlineArea;
    public static VBox onlineArea2;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messagesArea2 = messagesArea;
        onlineArea2 = onlineArea;
        selectedNode = null ;
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
        sendAllButton.setDisable(false);
        DeleteMessegeButton.setDisable(false);
        DeleteAllButton.setDisable(false);
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
        sendAllButton.setDisable(true);
        DeleteMessegeButton.setDisable(true);
        DeleteAllButton.setDisable(true);
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
//        remove(MainClass.mainUser)
        MainClass.mainUser = new User();
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
        MainClass.mainUser.setOnlineThread(new onlineStatusThread() );
        MainClass.mainUser.getOnlineThread().setWelcomeSocket();
        //  closing the thread
        ThreadGroup currentGroup = Thread.currentThread().getThreadGroup();
        int noThreads = currentGroup.activeCount();
        Thread[] lstThreads = new Thread[noThreads];
        currentGroup.enumerate(lstThreads);
        for (int i = 0; i < noThreads; i++) {
            Thread t = lstThreads[i];
            if (t.getName().equals("onlineThread")) {
                t.interrupt();
                return;
            }
        }
        Thread onlineThread= new Thread(MainClass.mainUser.getOnlineThread(),"onlineThread");
        onlineThread.start();
        System.out.println("1");

        String msg = "login%" + name + "%" + password + "%" + hostname + "%" + MainClass.mainUser.getOnlineServerPort()+"%"+MainClass.mainUser.getUDPServerPort();
        String response = MainClass.helper.sendToServer(serverIP, serverPort, msg);
        System.out.println("2");
        if(response.equals("failed")){
            JOptionPane.showMessageDialog(new JFrame(),"Wrong username or password","Alert",JOptionPane.ERROR_MESSAGE);
        }
        else{
            System.out.println("3");
            MainClass.mainUser.setTCPServerIP(serverIP);
            MainClass.mainUser.setTCPServerPort(serverPort);
//            MainClass.mainUser.setTCPServerIP(serverIP);
            System.out.println("4");
//            MainClass.mainUser.createUDPThread();
            Status.setText("Logged in successfully.");
            LocalIP.setText(hostname);
            LocalPort.setText(String.valueOf(MainClass.mainUser.getUDPServerPort()));
            loginEnable();
        }
    }
    @FXML
    void DeleteAll(ActionEvent event) throws IOException {
        for(Node message: messagesArea.getChildren()){
            if (message instanceof Label) {
                Label label = (Label) message;
                MainClass.helper.sendToServer(ServerIP.getText() , Integer.parseInt(ServerPort.getText()),"delete%"+label.getId());
//                    DeleteMessageByID(label.getId());

            }
        }
    }
    @FXML
    void logout(ActionEvent event) throws IOException {
        String name = usernameTestBox.getText();
        String serverIP = ServerIP.getText();
        int serverPort = Integer.parseInt(ServerPort.getText());
        String msg = "logout%" + name + "%" + MainClass.mainUser.getIP() + "%" + MainClass.mainUser.getUDPServerPort();
        MainClass.helper.sendToServer(serverIP, serverPort, msg);
        MainClass.helper.sendToServer(serverIP, serverPort, msg);
        messagesArea2.getChildren().clear();
        messagesArea.getChildren().clear();
        logoutEnable();
        clearAll();
    }
    @FXML
    void onSendButtonClick(ActionEvent eve) throws IOException {
        Random random = new Random();
        int randomNumber = random.nextInt(429496729);
        UDPClientThread.setFriendIP(RemoteIP.getText());
        UDPClientThread.setFriendPort(Integer.parseInt(RemotePort.getText()));
        UDPClientThread.setSentData(messageBox.getText()+'%'+randomNumber);
        UDPClientThread.sendData();
        Node label = new Label("Me: "+messageBox.getText());
        label.setId(String.valueOf(randomNumber));
        label.setStyle("-fx-text-fill: red;");
        label.setOnMouseClicked(event->selectNode(label));
        messagesArea.getChildren().add(label);
    }

    public static void DeleteMessageByID(String id){
        Platform.runLater(() -> {
            Label foundLabel = (Label) messagesArea2.lookup("#" + id);
            if (foundLabel != null) {
                messagesArea2.getChildren().remove(foundLabel);
            } else {
                System.out.println("Label not found");
            }
        });
//        deselectAll();
    }
    @FXML
    void DeleteMessage(ActionEvent event) throws IOException {
        if(selectedNode==null)
            return ;
        for(Node message: messagesArea.getChildren()){
            if (message instanceof Label) {
                Label label = (Label) message;
                if(selectedNode.getId().equals(label.getId()))
                {
                    MainClass.helper.sendToServer(ServerIP.getText() , Integer.parseInt(ServerPort.getText()),"delete%"+label.getId());
//                    DeleteMessageByID(label.getId());
                    break;
                }
            }
        }
    }
    private static void selectNode(Node node) {
        deselectAll();
        selectedNode = node ;
        node.setStyle("-fx-background-color: #9898d3;");
    }
    private static void deselectAll() {
        for (var child : messagesArea2.getChildren()) {
            if (child instanceof Label) {
                if(child!=null)
                    ((Label) child).setStyle("-fx-background-color: transparent;");
            }
        }
    }
    public static void receivedShow(String s){
        Platform.runLater(() -> {
            String data[] = s.split("%");
            Node label = new Label(data[0]);
            label.setId(data[1]);
            label.setStyle("-fx-text-fill: green;");
            label.setOnMouseClicked(event->selectNode(label));
            messagesArea2.getChildren().add(label);
        });
    }
    @FXML
    void updatePort(ActionEvent e) throws IOException {
        MainClass.mainUser.createUDPThread();
        LocalPort.setText(String.valueOf(MainClass.mainUser.getUDPServerPort()));
        Status.setText("Port updated successfully.");
    }


    public static void onlineUpdate(String s){

        Platform.runLater(() -> {
            onlineArea2.getChildren().clear();
            String data[] = s.split("%");
            for(int i =0 ;i< data.length ; i ++ ){
                String data2[] = data[i].split(",");
                onlineArea2.getChildren().add(new Label(data[i]));
            }

        });
    }

    @FXML
    void onSendAllButtonClick(ActionEvent e) throws IOException {
        Random random = new Random();
        int randomNumber = random.nextInt(429496729); // Generates a random integer between 0 (inclusive) and 100 (exclusive)
        Node msgLabel = new Label("Me: " + messageBox.getText());
        msgLabel.setId(String.valueOf(randomNumber));
        msgLabel.setOnMouseClicked(event -> selectNode(msgLabel));
        messagesArea.getChildren().add(msgLabel);
        for(Node onlineUser: onlineArea2.getChildren()){
            if (onlineUser instanceof Label) {
                Label label = (Label) onlineUser;
                String data[] = label.getText().split(",");
                if(label.getText().isBlank()||label.getText().isEmpty())
                        continue;
                System.out.println(label.getText());
                UDPClientThread.setFriendIP(data[1]);
                UDPClientThread.setFriendPort(Integer.parseInt(data[2]));
                UDPClientThread.setSentData(messageBox.getText()+'%'+randomNumber);
                UDPClientThread.sendData();
            }
        }
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
