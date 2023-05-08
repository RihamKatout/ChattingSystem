package com.client.p2pclient;

import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;


public class Chat {
    private User friend;
    private ArrayList<String> sentMessages, receivedMessages;
    private ArrayList<Boolean>sentOrReceived; //true => sent, false => received

    Chat() throws SocketException, UnknownHostException {
        friend = new User();
        sentMessages = new ArrayList<String>();
        receivedMessages = new ArrayList<String>();
        sentOrReceived = new ArrayList<Boolean>();
    }
    Chat(User friend) throws SocketException, UnknownHostException {
        this();
        this.friend = friend;
    }

    public void setFriend(User friend) {
        this.friend = friend;
    }

    public void setSentMessages(ArrayList<String> sentMessages) {
        this.sentMessages = sentMessages;
    }

    public void setReceivedMessages(ArrayList<String> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public void setSentOrReceived(ArrayList<Boolean> sentOrReceived) {
        this.sentOrReceived = sentOrReceived;
    }

    public User getFriend() {
        return friend;
    }

    public ArrayList<String> getSentMessages() {
        return sentMessages;
    }

    public ArrayList<String> getReceivedMessages() {
        return receivedMessages;
    }

    public ArrayList<Boolean> getSentOrReceived() {
        return sentOrReceived;
    }

    public boolean matchUser(User user2){
        return user2.equals(friend);
    }

    public void addReceivedMessage(String msg){
        sentOrReceived.add(false);
        receivedMessages.add(msg);
    }
}
