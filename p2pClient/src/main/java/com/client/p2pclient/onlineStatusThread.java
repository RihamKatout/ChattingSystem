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
        welcomeSocket = new ServerSocket(6789);
        running = true ;
        // test
    }

    @Override
    public void run() {
        synchronized (this) {
            while (running) {
                response = false ;
                try {
                    connectionSocket = welcomeSocket.accept();
                    inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                    outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                    onlineUsers = inFromClient.readLine();
//                    response = Login(clientLoginInfo);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    System.out.println(response);
                    outToClient.writeBytes(("Success") + '\n'); // it won't work without '\n' so just put it there
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(onlineUsers);
            }
        }
    }

}
