package com.client.p2pclient;

public class chat {
    private user friend;
    private String[] messages;

    public String[] getMessages() {
        return messages;
    }

    public user getFriend() {
        return friend;
    }

    public void setFriend(user friend) {
        this.friend = friend;
    }

    public void setMessages(String[] messages) {
        this.messages = messages;
    }
}
