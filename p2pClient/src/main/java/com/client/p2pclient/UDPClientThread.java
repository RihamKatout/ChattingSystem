package com.client.p2pclient;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class UDPClientThread{
    private static DatagramSocket socket;
    private static byte[] buffer, sentData;
    private static InetAddress friendIP;
    private static int friendPort;
    UDPClientThread() throws SocketException {
        socket = new DatagramSocket();
        buffer = new byte[1024];
        sentData = new byte[1024];
    }

    public static byte[] getBuffer() { return buffer; }

    public static byte[] getSentData() { return sentData; }

    public static void setBuffer(byte[] buffer) { UDPClientThread.buffer = buffer; }

    public static void setSentData(String sentData) { UDPClientThread.sentData = (MainClass.mainUser.getUsername()+"_"+sentData).getBytes(); }

    public static void setFriendIP(String friendIP) throws UnknownHostException { UDPClientThread.friendIP = InetAddress.getByName(friendIP); }

    public static void setFriendPort(int friendPort) { UDPClientThread.friendPort = friendPort; }

    public static void sendData() throws IOException {
        socket = new DatagramSocket();
        DatagramPacket sendPacket = new DatagramPacket(sentData, sentData.length, friendIP, friendPort);
        socket.send(sendPacket);
        socket.close();
    }
}
