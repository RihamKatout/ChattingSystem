package com.server.tcpserver;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class LoginThread implements Runnable {
    private ServerSocket welcomeSocket ;
    private String clientLoginInfo;
    private Socket connectionSocket;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;
    private Boolean running ,response;
    LoginThread() throws IOException {
        welcomeSocket = new ServerSocket(6789);
        running = true ;
        // test
    }

    @Override
    public void run() {
        synchronized (this) {
            while (running) {
                response = false ;
                try {
                    connectionSocket = welcomeSocket.accept();
                    inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
                    outToClient = new DataOutputStream(connectionSocket.getOutputStream());
                    clientLoginInfo = inFromClient.readLine();
                    response = Login(clientLoginInfo);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    System.out.println(response);
                    outToClient.writeBytes((response?"Success":"Failed" ) + '\n'); // it won't work without '\n' so just put it there
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    Boolean Login(String clientLoginInfo){
        System.out.println(clientLoginInfo);
        Boolean response = false ;
        BufferedReader reader; // for reading from a file
        String clientValues[] = clientLoginInfo.split(",");
        try {
            reader = new BufferedReader(new FileReader("src/main/resources/DataBase.txt"));
            String line = reader.readLine();
            while (line != null) {
                System.out.println(line);
                String dataBase[] = line.split(",");
                if ((dataBase[0]).equals(clientValues[0]) &&(dataBase[1]).equals(clientValues[1])  ) //validating name and password
                {
                    response = true;
                    break;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response ;
    }



}
