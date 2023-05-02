package com.client.p2pclient;

public class user {
    private String username, IP, port;

    public String getIP() {
        return IP;
    }

    public String getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public void setIP(String IP) {
        this.IP = IP;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean equals(user user2) {
        return IP.equals(user2.getIP()) && port.equals(user2.getPort());
    }

}
