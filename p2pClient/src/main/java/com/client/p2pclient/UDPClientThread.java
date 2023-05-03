package com.client.p2pclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClientThread{
    private static String myUsername;
    private static DatagramSocket socket;
    private static byte[] buffer, sentData;
    private boolean running;
    private static InetAddress IP, friendIP;
    private static int port, friendPort;
    UDPClientThread() throws SocketException {
        myUsername="Riham";
        running = true;
        socket = new DatagramSocket();
        buffer = new byte[1024];
        sentData = new byte[1024];
    }

    public static String getMyUsername() {
        return myUsername;
    }

    public static void setMyUsername(String myUsername) {
        UDPClientThread.myUsername = myUsername;
    }

    public static void setPort(int port) { UDPClientThread.port = port;}

    public static void setIP(InetAddress IP) { UDPClientThread.IP = IP; }

    public static InetAddress getIP() { return IP; }

    public static int getPort() { return port; }

    public static byte[] getBuffer() { return buffer; }

    public static byte[] getSentData() { return sentData; }

    public static void setBuffer(byte[] buffer) { UDPClientThread.buffer = buffer; }

    public static void setSentData(byte[] sentData) { UDPClientThread.sentData = sentData; }

    public static void setFriendIP(InetAddress friendIP) { UDPClientThread.friendIP = friendIP; }

    public static void setFriendPort(int friendPort) { UDPClientThread.friendPort = friendPort; }

    public static int getFriendPort() { return friendPort; }

    public static InetAddress getFriendIP() { return friendIP; }
    public static void sendData() throws IOException {
        socket = new DatagramSocket();
        DatagramPacket sendPacket = new DatagramPacket(sentData, sentData.length, friendIP, friendPort);
        socket.send(sendPacket);
        DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
        socket.receive(receivePacket); //receive friend's username
        socket.close();
    }
}
