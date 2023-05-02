package com.client.p2pclient;

import java.net.DatagramSocket;

public class UDPServerThread implements Runnable{
    private static DatagramSocket serverSocket;
    private byte[] receivedData, sentData;
    private static chat[]chats;
    private boolean running;
    UDPServerThread(){
        running = true;
    }
    @Override
    public void run(){
        synchronized(this){
            while(running){

            }
        }
    }
}
