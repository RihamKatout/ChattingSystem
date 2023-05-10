package com.server.tcpserver;

import java.io.*;
import java.lang.constant.Constable;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TCPServerThread implements Runnable {
    private ServerSocket welcomeSocket ;
    private String clientLoginInfo;
    private Socket connectionSocket;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;
    private String response;


    TCPServerThread(int port) throws IOException {
        welcomeSocket = new ServerSocket(port);
    }
    public ServerSocket getSocket(){
        return welcomeSocket;
    }

    private static void updateDataBase(String name , String IP, String UDPport , String OnlinePort ){
        BufferedReader reader; // for reading from a file
        try {
            reader = new BufferedReader(new FileReader("src/main/resources/DataBase.txt"));
            String line = reader.readLine();
            ArrayList<String> arr =new ArrayList<String>();
            StringBuilder content = new StringBuilder();
            while (line != null) {
                String dataBase[] = line.split(",");
                if (dataBase[0].equals(name) )
                {
                    content.append(dataBase[0]+','+dataBase[1]+','+IP+','+UDPport+','+OnlinePort);
                    content.append(System.lineSeparator());
                }
                else arr.add(line);
                line = reader.readLine();
            }
            BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/DataBase.txt"));
            String tmp = "";
            for(int i =0  ;i < arr.size(); i++){
                tmp+=arr.get(i) + '\n';
            }
            tmp+=content.toString();
            writer.write(tmp);
            writer.close();

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void run() {
        synchronized (this) {
            while (!welcomeSocket.isClosed()) {
                OutputStream outputStream;
                response = "failed" ;
                String message = "";
                try {
                    connectionSocket = welcomeSocket.accept();
                    System.out.println("accepted");
                    outputStream = connectionSocket.getOutputStream();
                    InputStream inputStream = connectionSocket.getInputStream();
                    message = HelperFunctions.reader(inputStream) ;
                    String data[] = message.split("%");
                    System.out.println(data[0]);
                    if(data[0].equals("login")) {
                        response = Login(data[1] + ',' + data[2] + ',' + data[3] + ',' + data[4] +',' +data[5]);

                    } else if (data[0].equals("status")) {
                        GUIController.newLog(data[1]);
                        response = "failed";
                    } else if (data[0].equals("logout")) {
                        response = Logout(data[1],data[2]);
                    }
                    else if(data[0].equals("port")){
                        response = updatePort(data[1], Integer.parseInt(data[2]));
                    }
                    else if(data[0].equals("delete")){
                        response = deleteMessage(data[1]);
                    }
                    System.out.println(response);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                try {
                    String answer = response ;
                    outputStream.write(answer.getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    String deleteMessage(String id ) throws IOException {
        for (User user : OnlineStatus.onlineUsers) {
            Socket clientSocketStatus = new Socket(user.getIP(), user.getOnlineStatusPort());
            OutputStream outputStreamStatus = clientSocketStatus.getOutputStream();
            InputStream inputStreamStatus = clientSocketStatus.getInputStream();
            outputStreamStatus.write(("#deleted%"+id).getBytes());
            System.out.println( HelperFunctions.reader(inputStreamStatus));
            clientSocketStatus.close();
        }
        return "deleted";
    }
    String Login(String clientLoginInfo) throws IOException {
        // name,pass,ip,onlinePort,UDPPort
        // 0   ,1   ,2 ,3         ,4
        System.out.println(clientLoginInfo);
        String response = "failed" ;
        BufferedReader reader; // for reading from a file
        String clientValues[] = clientLoginInfo.split(",");
        try {
            reader = new BufferedReader(new FileReader("src/main/resources/DataBase.txt"));
            String line = reader.readLine();
            while (line != null) {
                String dataBase[] = line.split(",");
                if (dataBase[0].equals(clientValues[0]) && dataBase[1].equals(clientValues[1]) ) //validating name and password
                {
                    System.out.println("hi "+line);
                    updateDataBase(clientValues[0],clientValues[2],clientValues[4],clientValues[3]);
                    OnlineStatus.newOnlineUser(new User(clientValues[0], clientValues[2],Integer.parseInt(clientValues[4]),Integer.parseInt(clientValues[3])));
//                    GUIController.newOnlineUser( dataBase[0]);//connectionSocket.getInetAddress().getHostAddress()
                    response = "success";
                    break;
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (response.equals("success")){
            GUIController.newLog(clientValues[0] + " just logged in");

//            OnlineStatus.newOnlineUser(clientValues[0]+','+clientValues[2] +','+ clientValues[4]); // 4 is the udp port
        }
        return response ;
    }
    String Logout(String name,String IP) throws IOException {
//        OnlineStatus.logOutUser(line);

        OnlineStatus.logOutUser(new User(name, IP));
        GUIController.newLog(name + " just logged out");
        return "logged out";
    }
    String updatePort(String name, int port) throws IOException {
        if(OnlineStatus.updatePort(name, port))
            return "port updated successfully";
        else
            return "couldn't update the port";
    }
}
