package com.client.p2pclient;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class onlineStatusThread implements Runnable {
    private ServerSocket welcomeSocket ;
    private String onlineUsers;
    private Socket connectionSocket;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;
    private Boolean running ,response;
    onlineStatusThread() throws IOException {

        running = true ;
        // test
    }
    public ServerSocket getWelcomeSocket() {
        return welcomeSocket;
    }

    public void setWelcomeSocket() throws IOException {

        //this.welcomeSocket = Helper.createSocket();
        MainClass.mainUser.setOnlineServerPort(welcomeSocket.getLocalPort());
    }
    @Override
    public void run() {
        synchronized (this) {
            while (!welcomeSocket.isClosed()) {
                OutputStream outputStream;
                try {
                    connectionSocket = welcomeSocket.accept();
                    outputStream = connectionSocket.getOutputStream();
                    InputStream inputStream = connectionSocket.getInputStream();
//                    System.out.println("hello");
                    String onlineUsers = Helper.reader(inputStream);
                    System.out.println(onlineUsers);
                    GUIController.onlineUpdate(onlineUsers);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    String answer = "thanks";
                    outputStream.write(answer.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
