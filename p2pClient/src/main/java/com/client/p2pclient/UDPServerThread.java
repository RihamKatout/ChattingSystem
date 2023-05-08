package com.client.p2pclient;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class UDPServerThread implements Runnable{
    private  DatagramSocket socket;

    private  DatagramPacket packet, sendPacket;
    private  byte[] buffer, sendData;
    private static ArrayList<Chat>chats;
    private  int port;
    private static boolean running;
    UDPServerThread(){
        running = true;
        buffer = new byte[1024];
        chats = new ArrayList<Chat>();
        sendData = new byte[1024];
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
                User user2 = null;
                try {
                    user2 = new User();
                } catch (SocketException e) {
                    throw new RuntimeException(e);
                } catch (UnknownHostException e) {
                    throw new RuntimeException(e);
                }
                int i = checkFriendship(user2);
                if(i==-1){
                    //create a new chat
                    try {
                        i = createChat(user2);
                    } catch (SocketException e) {
                        throw new RuntimeException(e);
                    } catch (UnknownHostException e) {
                        throw new RuntimeException(e);
                    }
                }
                String message = "";
                for(int t=0;t< input.length;t++)
                    message+=input[t];

                System.out.println(message);

                sendToGUI(message);
                addMessage(chats.get(i), message);
            }
            socket.close();
        }
    }

    public DatagramSocket getSocket() {
        return socket;
    }
    private void sendToGUI(String s){
        GUIController.receivedShow(s);
    }

    private int checkFriendship(User user2){
        for(int i=0;i<chats.size();i++){
            if(chats.get(i).matchUser(user2))
                return i;
        }
        return -1;
    }
    private int createChat(User friend) throws SocketException, UnknownHostException {
        Chat newChat = new Chat(friend);
        chats.add(newChat);
        return chats.size()-1;
    }
    private void addMessage(Chat targetChat, String msg){
        targetChat.addReceivedMessage(msg);
    }

    public void setPort(int Port) throws SocketException {
        port = Port;
        socket = new DatagramSocket(port);
    }

    public int getPort() {
        return port;
    }
}
