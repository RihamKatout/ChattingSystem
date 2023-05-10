package com.server.tcpserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class OnlineStatus {
    public static ArrayList<User> onlineUsers = new ArrayList<>();

    private ServerSocket welcomeSocket ;
    private String clientLoginInfo;
    private Socket connectionSocket;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;

     public static void updateClients() throws IOException {
         System.out.println("All online users: ");
         for(var user : onlineUsers){
             System.out.println("\t"+user.getUsername() + ", " + user.getIP() + ", " + user.getPort());
         }
         for (User user : onlineUsers) {
             String allOnlineUsers = "%";
             for (User online : onlineUsers) {
                if(online.equals(user)) {
                    continue;
                }
                allOnlineUsers += online.getUsername()+',' + online.getIP()+',' + online.getPort() +'%';
            }
            Socket clientSocketStatus = new Socket(user.getIP(), user.getOnlineStatusPort());
            OutputStream outputStreamStatus = clientSocketStatus.getOutputStream();
            InputStream inputStreamStatus = clientSocketStatus.getInputStream();
            outputStreamStatus.write(allOnlineUsers.getBytes());
            System.out.println( HelperFunctions.reader(inputStreamStatus));
            clientSocketStatus.close();
        }

    }
    public static void newOnlineUser(User user) throws IOException {
         onlineUsers.add(user);
         updateClients();
         GUIController.newOnlineUser(user.getUsername());
    }
    public static void logOutUser(User user) throws IOException {
         GUIController.deleteOnlineUser(user.getUsername());
         int index=0;
         for(var user2 : onlineUsers){
             if(user2.getIP().equals(user.getIP())&&user2.getUsername().equals(user.getUsername())){
                 onlineUsers.remove(index);
                 break;
             }
             index++;
         }

         System.out.println("deleted");
         updateClients();
    }
    public static boolean updatePort(String name, int port) throws IOException {
        for(var user : onlineUsers){
            if(user.getUsername().equals(name)){
                user.setPort(port);
                updateClients();
                return true;
            }
        }
        return false;
    }

}
