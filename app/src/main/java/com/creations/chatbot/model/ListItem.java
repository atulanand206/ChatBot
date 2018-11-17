package com.creations.chatbot.model;

import java.util.Random;

public class ListItem {

    private boolean isUser;

    private Message message;

    public ListItem() {
        isUser = new Random().nextBoolean();
        message = new Message();
    }

    public ListItem(String newEntry) {
        isUser = true;
        message = new Message(newEntry);
    }


    public boolean isUser() {
        return isUser;
    }

    public void setUser(boolean user) {
        isUser = user;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" +
                "\"isUser\":" + isUser +
                ",\"message\":" + message +
                '}';
    }
}
