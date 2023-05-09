package com.server.tcpserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class OnlineStatus {
    private static ArrayList<User> onlineUsers = new ArrayList<>();

    private ServerSocket welcomeSocket ;
    private String clientLoginInfo;
    private Socket connectionSocket;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;

     public static void updateClients() throws IOException {
         System.out.println("All online users: ");
         for(var user : onlineUsers){
             System.out.println(user.getUsername() + ", " + user.getIP() + ", " + user.getPort());
         }
//         for (String user : onlineUsers) {
//             String allOnlineUsers = "online users :  ";
//             for (String online : onlineUsers) {
//                if(online.equals(user)) {
//                    continue;
//                }
//                allOnlineUsers += online +'%';
//            }
//            String userData[] = user.split(",");
//            Socket clientSocketStatus = new Socket(userData[1], Integer.parseInt(userData[4]));
//            OutputStream outputStreamStatus = clientSocketStatus.getOutputStream();
//            InputStream inputStreamStatus = clientSocketStatus.getInputStream();
//            outputStreamStatus.write(allOnlineUsers.getBytes());
//            System.out.println( HelperFunctions.reader(inputStreamStatus));
//            clientSocketStatus.close();
//        }

    }
    public static void newOnlineUser(User user) throws IOException {
         GUIController.newOnlineUser(user.getUsername());
         onlineUsers.add(user);
         updateClients();
    }
    public static void logOutUser(User user) throws IOException {
         GUIController.deleteOnlineUser(user.getUsername());
         int index=0;
         for(var user2 : onlineUsers){
             if(user2.equals(user)){
                 break;
             }
             index++;
         }
         onlineUsers.remove(index);
         System.out.println("deleted");
         updateClients();
    }
    public static boolean updatePort(String name, int port){
        for(var user : onlineUsers){
            if(user.getUsername().equals(name)){
                user.setPort(port);
                return true;
            }
        }
        return false;
    }

}
