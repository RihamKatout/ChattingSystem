package com.client.p2pclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class UDPServerThread implements Runnable{
    private static DatagramSocket socket;
    private static DatagramPacket packet, sendPacket;
    private static byte[] buffer, sendData;
    private static ArrayList<chat>chats;
    private static int port = 9876;
    private static boolean running;

    private static InetAddress IP;
    UDPServerThread() throws SocketException {
        running = true;
        socket = new DatagramSocket(port);
        buffer = new byte[1024];
        packet = new DatagramPacket(buffer, buffer.length);
        chats = new ArrayList<chat>();
        sendData = new byte[1024];
    }
    @Override
    public void run(){
        synchronized(this){
            while(running){
                //receivedData Form : username_message
                try {
                    socket.receive(packet);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String []input = (new String(packet.getData(), 0, packet.getLength())).split("_");
                InetAddress friendIPAddress = packet.getAddress();
                int friendPort = packet.getPort();

                //check if the user is a friend
                User user2 = new User(friendIPAddress.toString(), friendPort, input[0]);
                int i = checkFriendship(user2);
                if(i==-1){
                    //create a new chat
                    i = createChat(user2);
                }
                String message = input[1];
                for(int t=2;t< input.length;t++)
                    message+=input[t];

                addMessage(chats.get(i), message);
                sendData = UDPClientThread.getMyUsername().getBytes();
                sendPacket=new DatagramPacket(sendData, sendData.length, friendIPAddress, friendPort);
                try {
                    socket.send(sendPacket);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            socket.close();
        }
    }

    private int checkFriendship(User user2){
        for(int i=0;i<chats.size();i++){
            if(chats.get(i).matchUser(user2))
                return i;
        }
        return -1;
    }
    private int createChat(User friend){
        chat newChat = new chat(friend);
        chats.add(newChat);
        return chats.size()-1;
    }
    private void addMessage(chat targetChat, String msg){
        targetChat.addReceivedMessage(msg);
    }

    public static void setIP(InetAddress IP) {
        UDPServerThread.IP = IP;
    }

    public static InetAddress getIP() {
        return IP;
    }

    public static void setPort(int port) {
        UDPServerThread.port = port;
    }

    public static int getPort() {
        return port;
    }
}
