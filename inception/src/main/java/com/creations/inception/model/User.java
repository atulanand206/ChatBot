package com.creations.inception.model;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    @PrimaryKey
    private String user;

    private RealmList<ListItem> messages;

    public User() {
    }

    public User(String user) {
        this.user = user;
        this.messages = new RealmList<>();
    }

    public User(String user, RealmList<ListItem> messages) {
        this.user = user;
        this.messages = messages;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public RealmList<ListItem> getMessages() {
        return messages;
    }

    public void setMessages(RealmList<ListItem> messages) {
        this.messages = messages;
    }

    @Override
    public String toString() {
        return "{" +
                "\"user\":" + user +
                ",\"messages\":" + messages +
                '}';
    }
}
