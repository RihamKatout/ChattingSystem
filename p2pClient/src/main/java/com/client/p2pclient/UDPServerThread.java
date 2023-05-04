package com.client.p2pclient;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class UDPServerThread implements Runnable{
    private static DatagramSocket socket;
    private static DatagramPacket packet, sendPacket;
    private static byte[] buffer, sendData;
    private static ArrayList<chat>chats;
    private static int port;
    private static boolean running;

    UDPServerThread() throws SocketException, UnknownHostException {
        running = true;
        port = 1218;
        buffer = new byte[1024];
        chats = new ArrayList<chat>();
        sendData = new byte[1024];
        socket = new DatagramSocket(port);
        packet = new DatagramPacket(buffer, buffer.length);
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
                user user2 = new user(friendIPAddress.toString(), friendPort);
                int i = checkFriendship(user2);
                if(i==-1){
                    //create a new chat
                    i = createChat(user2);
                }
                String message = "";
                for(int t=0;t< input.length;t++)
                    message+=input[t];

                System.out.println(message);
                addMessage(chats.get(i), message);
            }
            socket.close();
        }
    }

    private int checkFriendship(user user2){
        for(int i=0;i<chats.size();i++){
            if(chats.get(i).matchUser(user2))
                return i;
        }
        return -1;
    }
    private int createChat(user friend){
        chat newChat = new chat(friend);
        chats.add(newChat);
        return chats.size()-1;
    }
    private void addMessage(chat targetChat, String msg){
        targetChat.addReceivedMessage(msg);
    }

    public static void setPort(int port) {
        UDPServerThread.port = port;
    }

    public static int getPort() {
        return port;
    }
}
