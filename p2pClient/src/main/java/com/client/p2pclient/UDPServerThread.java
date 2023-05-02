package com.client.p2pclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class UDPServerThread implements Runnable{
    private static DatagramSocket socket;
    private static DatagramPacket packet;
    private static byte[] buffer;
    private static ArrayList<chat>chats;
    private static int port = 9876;
    private static boolean running;


    UDPServerThread() throws SocketException {
        running = true;
        socket = new DatagramSocket(port);
        buffer = new byte[1024];
        packet = new DatagramPacket(buffer, buffer.length);
        chats = new ArrayList<chat>();
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
                user user2 = new user(friendIPAddress.toString(), friendPort, input[0]);
                int i = checkFriendship(user2);
                if(i==-1){
                    //create a new chat
                    i = createChat(user2);
                }

                addMessage(chats.get(i), input[1]);

            }
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
}
