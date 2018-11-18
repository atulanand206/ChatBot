package com.creations.chatbot.model;

import java.util.Random;

import io.realm.RealmObject;

public class ListItem extends RealmObject{

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

    public ListItem(APIResponse response) {
        isUser = false;
        message = new Message(response);
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
