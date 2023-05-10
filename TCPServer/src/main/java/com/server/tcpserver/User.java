package com.server.tcpserver;

import java.io.IOException;
import java.net.*;

public class User {
    private String username, IP;
    private int UDPServerPort;
    private int onlineStatusPort ;
    User(){
        IP = username = null;
        UDPServerPort = 0 ;
        onlineStatusPort = 0 ;
    }
    User(String username){
        this();
        this.username = username;
    }
    User(String username, String IP ){
        UDPServerPort = 0;
        this.username = username;
        this.IP = IP;
    }
    User(String username, String IP, int UDPServerPort,int OnlineServerPort){
        this.IP = IP;
        this.UDPServerPort = UDPServerPort;
        this.username = username;
        this.onlineStatusPort = OnlineServerPort;

    }
    public int getOnlineStatusPort() {
        return onlineStatusPort;
    }

    public void setOnlineStatusPort(int onlineStatusPort) {
        this.onlineStatusPort = onlineStatusPort;
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
    public void setPort(int port){
        this.UDPServerPort = port;
    }
    public int getPort() {
        return UDPServerPort;
    }

    public boolean equals(User user2) {
        return user2.getUsername().equals(getUsername()) && user2.getIP().equals(getIP()) && user2.getPort() == getPort();
    }

}