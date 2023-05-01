package com.client.p2pclient;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class ServerHelper {
    private  Socket clientSocket ;
    private  OutputStream outputStream;
    private  InputStream inputStream;
    ServerHelper() throws IOException {
        clientSocket = new Socket("192.168.1.10", 6789);
        outputStream = clientSocket.getOutputStream();
        inputStream = clientSocket.getInputStream();
    }
    private  String reader(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        String response = new String(buffer, 0, bytesRead);
        return response;
    }
    public void sendToServer(Request type,String message) throws IOException {
        String req ="";
        switch (type){
            case LOG_IN -> req = "1,"+message;
            case LOG_OUT -> req = "2,"+message;
            case MESSAGE_SENT -> req = "3,"+message;
        }
        outputStream.write(req.getBytes());
        System.out.println("Server response: " + reader(inputStream));
    }
}
