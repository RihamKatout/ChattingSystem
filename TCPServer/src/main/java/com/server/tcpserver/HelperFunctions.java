package com.server.tcpserver;

import java.io.IOException;
import java.io.InputStream;

public class HelperFunctions {
    public static String reader(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        String response = new String(buffer, 0, bytesRead);
        return response;
    }
}
