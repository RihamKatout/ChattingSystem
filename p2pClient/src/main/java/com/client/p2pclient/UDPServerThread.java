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
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                String []input = (new String(packet.getData(), 0, packet.getLength())).split("_");
                String message = "";
                for(int t=0;t< input.length;t++)
                    message+=input[t];

                System.out.println(message);

                sendToGUI(message);
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
        socket = new DatagramSocket(port);
    }
}
