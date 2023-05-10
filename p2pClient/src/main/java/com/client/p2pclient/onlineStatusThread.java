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
        this.welcomeSocket = MainClass.helper.createValidSocket();
        MainClass.mainUser.setOnlineServerPort(welcomeSocket.getLocalPort());
    }
    @Override
    public void run() {
        synchronized (this) {
            while (!welcomeSocket.isClosed()) {
                OutputStream outputStream;
                try {
                    connectionSocket = welcomeSocket.accept();
                    System.out.println("updates on online users !");
                    outputStream = connectionSocket.getOutputStream();
                    InputStream inputStream = connectionSocket.getInputStream();
                    String onlineUsers = Helper.reader(inputStream);
                    System.out.println(onlineUsers);
                    if(onlineUsers.contains("#deleted")){
                        String data[] = onlineUsers.split("%");
                        GUIController.DeleteMessageByID(data[1]);
                    }
                    else {
                        GUIController.onlineUpdate(onlineUsers);
                        System.out.println("updated");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    String answer = "thanks";
                    outputStream.write(answer.getBytes());
                    System.out.println("finished update on online users");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}
