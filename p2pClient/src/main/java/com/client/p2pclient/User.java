package com.client.p2pclient;

import java.io.IOException;
import java.net.*;

public class User {
    private String username, IP, TCPServerIP;
    private UDPServerThread UDPServer;
    private int onlineServerPort, port;
    private onlineStatusThread onlineThread ;

    User() throws SocketException, UnknownHostException {
        IP = username = null;
        UDPServer = null;
        port = 0 ;
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
    public void setPort() throws IOException {
        for(int ports = 1 ; ports <= 9999; ports++){
            try {
                DatagramSocket tmp = new DatagramSocket(ports);
                tmp.close();
                this.port = ports;
                return;
            } catch (IOException ex) {
                System.out.println("port is not valid");
                continue;
            }
        }
        throw new IOException("no free port found");
    }
    public int getPort() {
        return port;
    }
    public void setTCPServerIP(String TCPServerIP) {
        this.TCPServerIP = TCPServerIP;
    }
    public void createUDPThread() throws IOException {
        if(UDPServer != null) {
            if (UDPServer.getSocket().isClosed())
                UDPServer.getSocket().close();
        }
        UDPServer = new UDPServerThread();
        setPort();
        UDPServer.setPort(port);
//        UDPServer.getSocket().connect(InetAddress.getByName((InetAddress.getLocalHost().getHostAddress())),port);
        Thread thread = new Thread(UDPServer);
        thread.start();
        System.out.println("UDP created successfully");
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
        return IP.equals(user2.getIP()) && port==(user2.getPort());
    }

}