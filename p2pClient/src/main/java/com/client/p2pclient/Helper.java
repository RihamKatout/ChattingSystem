package com.client.p2pclient;

import java.io.*;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class Helper {
    private  Socket TCPSocket;
    private String serverIP;
    private int serverPort;
    private InputStream inFromServer;
    private OutputStream outToServer;

    public String getServerIP() {
        return serverIP;
    }

    public int getServerPort() {
        return serverPort;
    }

    private void setServerIP(String serverIp) {
        this.serverIP = serverIp;
    }

    private void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }
    public ServerSocket createValidSocket() throws IOException {
        for(int ports = 1 ; ports <= 9999; ports++) {
            try {
                ServerSocket tmp = new ServerSocket(ports);
                return tmp;
            } catch (IOException ex) {
                continue;
            }
        }
        return null ;
    }

    private Socket createSocket(String serverIP, int serverPort) throws IOException {
        setServerIP(serverIP);
        setServerPort(serverPort);
        TCPSocket = new Socket(MainClass.mainUser.getTCPServerIP(), MainClass.mainUser.getTCPServerPort());
        return TCPSocket;
    }
    public static  String reader(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        String response = new String(buffer, 0, bytesRead);
        return response;
    }
    public String sendToServer(String IP ,int port, String message) throws IOException {
        createSocket(IP, port);
        inFromServer = TCPSocket.getInputStream();
        outToServer = TCPSocket.getOutputStream();
        outToServer.write(message.getBytes());
        String answer = reader(inFromServer);
        System.out.println("Server response: " + answer);
        TCPSocket.close();
        return answer ;
    }
}
