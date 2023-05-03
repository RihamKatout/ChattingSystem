package com.client.p2pclient;

import java.util.ArrayList;


public class chat {
    private user friend;
    private ArrayList<String> sentMessages, receivedMessages;
    private ArrayList<Boolean>sentOrReceived; //true => sent, false => received

    chat(){
        friend = new user();
        sentMessages = new ArrayList<String>();
        receivedMessages = new ArrayList<String>();
        sentOrReceived = new ArrayList<Boolean>();
    }
    chat(user friend){
        this();
        this.friend = friend;
    }

    public void setFriend(user friend) {
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

    public user getFriend() {
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

    public boolean matchUser(user user2){
        return user2.equals(friend);
    }

    public void addReceivedMessage(String msg){
        sentOrReceived.add(false);
        receivedMessages.add(msg);
    }
}
