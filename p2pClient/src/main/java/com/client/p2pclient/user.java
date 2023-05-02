package com.client.p2pclient;

public class user {
    private String username, IP;
    private int port;

    user(){
        IP = username = null;
        port = 0;
    }
    user(String IP, int port){
        this.IP = IP;
        this.port = port;
    }
    user(String IP, int port, String username){
        this(IP, port);
        this.username = username;
    }
    public String getIP() {
        return IP;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean equals(user user2) {
        return IP.equals(user2.getIP()) && port==(user2.getPort());
    }

}