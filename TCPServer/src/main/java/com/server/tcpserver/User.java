package com.server.tcpserver;

import java.io.IOException;
import java.net.*;

public class User {
    private String username, IP;
    private int port;

    User(){
        IP = username = null;
        port = 0 ;
    }
    User(String username){
        this();
        this.username = username;
    }
    User(String username, String IP){
        port = 0;
        this.username = username;
        this.IP = IP;
    }
    User(String username, String IP, int port){
        this.IP = IP;
        this.port = port;
        this.username = username;

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
        this.port = port;
    }
    public int getPort() {
        return port;
    }

    public boolean equals(User user2) {
        return user2.getUsername().equals(getUsername());
    }

}