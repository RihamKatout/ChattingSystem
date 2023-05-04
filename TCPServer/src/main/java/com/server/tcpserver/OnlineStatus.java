package com.server.tcpserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class OnlineStatus {
    private static Set<String> onlineUsers = new HashSet<>();

    private ServerSocket welcomeSocket ;
    private String clientLoginInfo;
    private Socket connectionSocket;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;

     public static void updateClients() throws IOException {
         for (String user : onlineUsers) {
             String allOnlineUsers = "online users :  ";
             for (String online : onlineUsers) {
                if(online.equals(user)) {
                    continue;
                }
                allOnlineUsers += online +'$';
            }
            String userData[] = user.split(",");
            Socket clientSocketStatus = new Socket(userData[2], Integer.parseInt(userData[3]));
            OutputStream outputStreamStatus = clientSocketStatus.getOutputStream();
            InputStream inputStreamStatus = clientSocketStatus.getInputStream();
            outputStreamStatus.write(allOnlineUsers.getBytes());
            System.out.println( HelperFunctions.reader(inputStreamStatus));
            clientSocketStatus.close();
        }

    }
    public static void newOnlineUser(String userDetails) throws IOException {
        onlineUsers.add(userDetails);
        updateClients();
    }
    public static void logOutUser(String userDetails) throws IOException {
        onlineUsers.remove(userDetails);
        updateClients();
    }


}
