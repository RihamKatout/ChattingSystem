package com.client.p2pclient;

public class UDPClientThread implements Runnable{
    private static String myUsername;
    private boolean running;
    UDPClientThread(){
        myUsername="Riham";
        running = true;
    }

    public static String getMyUsername() {
        return myUsername;
    }

    public static void setMyUsername(String myUsername) {
        UDPClientThread.myUsername = myUsername;
    }


    @Override
    public void run() {
        synchronized (this){
            while(running){
                
            }
        }
    }
}
