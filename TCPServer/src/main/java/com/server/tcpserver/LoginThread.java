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
    private Boolean response;
    LoginThread() throws IOException {
        welcomeSocket = new ServerSocket(6789);
    }
    @Override
    public void run() {
        synchronized (this) {
            while (!welcomeSocket.isClosed()) {
                OutputStream outputStream;
                response = false ;
                String message = "";
                try {
                    connectionSocket = welcomeSocket.accept();
                    System.out.println("accepeted");
                    outputStream = connectionSocket.getOutputStream();
                    InputStream inputStream = connectionSocket.getInputStream();
                    message = HelperFunctions.reader(inputStream) ;
                    response = Login(message);
                    System.out.println(response);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    String answer = response ? "success":"failed";
                    outputStream.write(answer.getBytes());
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
                String dataBase[] = line.split(",");
                if ((dataBase[0]).equals(clientValues[0])   ) //validating name and password
                {
                    System.out.println("hi "+line);
                    OnlineStatus.newOnlineUser(line);
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
