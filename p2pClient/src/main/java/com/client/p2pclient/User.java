package com.client.p2pclient;

import java.io.IOException;
import java.net.*;

public class User {
    private String username;
    private String IP;

    public String getTCPServerIP() {
        return TCPServerIP;
    }

    private String TCPServerIP;

    public int getTCPServerPort() {
        return TCPServerPort;
    }

    public void setTCPServerPort(int TCPServerPort) {
        this.TCPServerPort = TCPServerPort;
    }

    private int TCPServerPort;
    private UDPServerThread UDPServer;
    private int onlineServerPort, UDPServerPort;

    private onlineStatusThread onlineThread ;

    User() throws SocketException, UnknownHostException {
        IP = username = null;
        UDPServer = null;
        UDPServerPort = 0 ;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return username;
    }
    public void setIP(String IP) {
        this.IP = IP;
    }
    public String getIP() {
        return IP;
    }
    public void setUDPServerPort(int noPort) throws IOException {
        for(int ports = 1 ; ports <= 9999; ports++){
            try {
                DatagramSocket tmp = new DatagramSocket(ports);
                tmp.close();
                if(ports==noPort)continue;
                this.UDPServerPort = ports;
                return;
            } catch (IOException ex) {
                continue;
            }
        }
        throw new IOException("no free port found");
    }
    public int getUDPServerPort() {
        return UDPServerPort;
    }
    public void setTCPServerIP(String TCPServerIP) {
        this.TCPServerIP = TCPServerIP;
    }
    public void createUDPThread() throws IOException {
        int noPort = 0;
        if(UDPServer != null && !UDPServer.getSocket().isClosed()){
            noPort = getUDPServerPort();
            UDPServer.getSocket().close();
        }
        UDPServer = new UDPServerThread();
        setUDPServerPort(noPort);
        UDPServer.setPort(UDPServerPort);
        Thread thread = new Thread(UDPServer);
        thread.start();
        System.out.println("UDP created successfully");
    }
    public onlineStatusThread getOnlineThread() {
        return onlineThread;
    }

    public void setOnlineThread(onlineStatusThread onlineThread) {
        this.onlineThread = onlineThread;
    }
    public UDPServerThread getUDPServer(){
        return UDPServer;
    }
    ////////////////////////////////////////////////
    public void setOnlineServerPort(int onlineServerPort) {
        this.onlineServerPort = onlineServerPort;
    }
    public int getOnlineServerPort() {
        return onlineServerPort;
    }
    public boolean equals(User user2) {
        return IP.equals(user2.getIP()) && UDPServerPort ==(user2.getUDPServerPort());
    }

}