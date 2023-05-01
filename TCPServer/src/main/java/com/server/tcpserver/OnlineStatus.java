package com.server.tcpserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class OnlineStatus{
    static  Set<String> onlineUsers = new HashSet<>();

    static void updateClients() throws IOException {
        for (String user : onlineUsers) {
            System.out.println(user);

            String userData[] = user.split(",");
//            userData[0]; // name
//            userData[1]; // email
//            userData[2]; // ip
//            userData[3]; // port
            String sentenceFromClient;
            String responseFromServer;
            Socket clientSocket = new Socket(userData[2], Integer.parseInt(userData[3])); // different port
            DataOutputStream outToServer =
                    new DataOutputStream(clientSocket.getOutputStream());
            BufferedReader inFromServer =
                    new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            sentenceFromClient = "";
            for (String onlineUsers : onlineUsers){
                if(onlineUsers.equals(user))
                    continue;
                sentenceFromClient += user +'$';
            }
            System.out.println("FROM SERVER: " + sentenceFromClient);
            outToServer.writeBytes(sentenceFromClient + '\n');
          responseFromServer = inFromServer.readLine();
            clientSocket.close();
        }

    }
    public static void newOnlineUser(String userDetails) throws IOException {
        onlineUsers.add(userDetails);
//        updateClients();

    }
}
