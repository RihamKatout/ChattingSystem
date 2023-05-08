package com.client.p2pclient;

import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class User {
    private String username, IP;
    private int port ;

    private int onlineServerPort ;
    private int UDPServerport;

    private UDPServerThread UDPserver ;
    private onlineStatusThread onlineThread ;
    private String TCPServerIp ;

    public void setTCPServerIp(String TCPServerIp) {
        this.TCPServerIp = TCPServerIp;
    }

    public int getOnlineServerPort() {
        return onlineServerPort;
    }

    public void setOnlineServerPort(int onlineServerPort) {
        this.onlineServerPort = onlineServerPort;
    }

    User() throws SocketException, UnknownHostException {
        IP = username = null;
        port = 0 ;
        UDPserver = new UDPServerThread();
    }


    User(String IP, int port){
        this.IP = IP;
        this.port = port;
    }
    User(String IP, int port, String username){
        this(IP, port);
        this.username = username;
    }
    void createUDPThraed(int port) throws SocketException, UnknownHostException {
//        UDPserver.setPort(port);
        if(!UDPserver.getSocket().isClosed())
            UDPserver.getSocket().close();
        UDPserver.getSocket().connect(InetAddress.getByName((InetAddress.getLocalHost().getHostAddress())),port);
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

    public boolean equals(User user2) {
        return IP.equals(user2.getIP()) && port==(user2.getPort());
    }

}