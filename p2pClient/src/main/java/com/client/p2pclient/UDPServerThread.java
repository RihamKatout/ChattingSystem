package com.client.p2pclient;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class UDPServerThread implements Runnable{
    private  DatagramSocket socket;

    private  DatagramPacket packet, sendPacket;
    private  byte[] buffer;
    private  int port;
    private static boolean running;
    UDPServerThread(){
        running = true;
        buffer = new byte[1024];
        packet = new DatagramPacket(buffer, buffer.length);
    }

    public int getPort() {
        return port;
    }

    @Override
    public void run(){
        synchronized(this){
            while(running){
                //receivedData Form : username_message
                try {
                    socket.receive(packet);
                    System.out.println("received");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                int t=0;
                String input = (new String(packet.getData(), 0, packet.getLength()));
                String message = "", friendUsername = "";
                for(;t< input.length();t++) {
                    if(input.charAt(t) == ',')
                        break;
                    friendUsername+=input.charAt(t);
                }
                message = input.substring(t+1);
                System.out.println("Received from" + friendUsername + ": " + message);

                sendToGUI(friendUsername + ": " + message);
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
    public void setPort(int Port) throws SocketException {

        port = Port;
        if(socket!=null )
            if(!socket.isClosed())
                socket.close();
        System.out.println(port);
        socket = new DatagramSocket(port);
    }
}
