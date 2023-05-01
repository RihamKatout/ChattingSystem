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
    private static String reader(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        String response = new String(buffer, 0, bytesRead);
        return response;
    }
    @Override
    public void run() {
        synchronized (this) {
            while (!welcomeSocket.isClosed()) {
                OutputStream outputStream;
                response = false ;
                try {
                    connectionSocket = welcomeSocket.accept();
                    outputStream = connectionSocket.getOutputStream();
                    InputStream inputStream = connectionSocket.getInputStream();
                    response = Login(reader(inputStream));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    System.out.println(response);
                    String answer = response? "success":"failed";
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
                System.out.println(line);
                String dataBase[] = line.split(",");
                if ((dataBase[0]).equals(clientValues[0])   ) //validating name and password
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
