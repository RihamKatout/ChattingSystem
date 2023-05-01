package com.server.tcpserver;

import java.util.HashSet;
import java.util.Set;

public class OnlineStatusThread extends Thread{
    Set<Integer> onlineUsers ;

    OnlineStatusThread(){
        Set<Integer> onlineUsers = new HashSet<>();

    }
    @Override
    public void run() {

    }
}
