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
    public void sendToServer(Request type,String message) throws IOException {
        clientSocketLogIn = new Socket("172.19.203.142", 1218);
        outputStreamLogIn = clientSocketLogIn.getOutputStream();
        inputStreamLogIn = clientSocketLogIn.getInputStream();
        String req = message;
        outputStreamLogIn.write(req.getBytes());
        System.out.println("Server response: " +   reader(inputStreamLogIn));
        clientSocketLogIn.close();
    }
}
