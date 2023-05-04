package com.client.p2pclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerHelper {
    private  Socket clientSocketLogIn , clientSocketStatus ;
    private  OutputStream outputStreamLogIn,outputStreamStatus;
    private  InputStream inputStreamLogIn,inputStreamStatus;
    ServerHelper() throws IOException {


    }
    public static  String reader(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        String response = new String(buffer, 0, bytesRead);
        return response;
    }
    public String sendToLoginServer(String name ,String password,String ip ,int port) throws IOException {
        if(name.contains(",")||password.contains(",")||name.contains("$")||password.contains("$"))
                return "failed";
        clientSocketLogIn = new Socket(ip, port);
        outputStreamLogIn = clientSocketLogIn.getOutputStream();
        inputStreamLogIn = clientSocketLogIn.getInputStream();
        String req = name + ','+password;
        outputStreamLogIn.write(req.getBytes());
        String answer = reader(inputStreamLogIn) ;
        System.out.println("Server response: " + answer);
        clientSocketLogIn.close();
        return answer ;
    }
}
