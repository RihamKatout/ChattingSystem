package com.server.tcpserver;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class GUIController implements Initializable {

    @FXML
    private Button listenButton;

    @FXML
    private VBox logArea;
    public static VBox logAreaGlobal;

    @FXML
    private VBox onlineArea;
    private static VBox onlineAreaGlobal;

    @FXML
    private TextArea portTextbox;

    @FXML
    private Label statusLabel;
    @FXML
    private Label unvalidPort;

    private TCPServerThread TCPServerThread;
    private Thread TCPThread ;

    private Boolean validPort(String port)  {
        if(port.length()!=4)
            return false ;
        try {
            ServerSocket temp = new ServerSocket(Integer.parseInt(port));
            temp.close();
        } catch (Exception ex) {
            return false;
        }
        return true ;
    }
    private static SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
    private static Date date = new Date();
    public static void newLog(String s){
        date = new Date() ;
        Platform.runLater(() -> {
            logAreaGlobal.getChildren().add(new Label(s + "- " + formatter.format(date)));
        });
    }
    public static void newOnlineUser(String s){
        Platform.runLater(() -> {
            onlineAreaGlobal.getChildren().add(new Label(s));
//            Label x = new Label("g");
        });
    }
    public static void deleteOnlineUser(String s){
        Platform.runLater(() -> {
            for(Node onlineUser: onlineAreaGlobal.getChildren()){
                if (onlineUser instanceof Label) {
                    Label label = (Label) onlineUser;
                    if (label.getText().equals(s)) {
                        onlineAreaGlobal.getChildren().remove(label);
                        break;
                    }
                }
            }
        });
    }
    @FXML
    void listening(ActionEvent event) throws IOException {
        String port = portTextbox.getText();
        unvalidPort.setVisible(false);

        if(!validPort(port)){
            unvalidPort.setVisible(true);
            return ;
        }
        if(TCPThread!=null) {
            if(TCPThread.isAlive()){
                TCPThread.interrupt();
            }
        }
        TCPServerThread= new TCPServerThread(Integer.parseInt(port));
        TCPThread = new Thread(TCPServerThread);
        TCPThread.start();
        System.out.println("valid");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        InetAddress ip;
        String hostname = "null";
        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostAddress();
//            System.out.println("Your current IP address : " + hostname);
        } catch (UnknownHostException e) {

        }
        statusLabel.setText(hostname);
        logAreaGlobal = logArea ;
        onlineAreaGlobal = onlineArea ;
    }
}
