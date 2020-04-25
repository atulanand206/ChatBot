package com.creations.chatbot.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Request extends RealmObject {

    private String apiKey;

    private String chatBotID;

    private String externalID;

    private String message;

    private String firstName;

    private String lastName;

    private String gender;

    private boolean isLeft = false;

    @PrimaryKey
    private int isLeftId;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getChatBotID() {
        return chatBotID;
    }

    public void setChatBotID(String chatBotID) {
        this.chatBotID = chatBotID;
    }

    public String getExternalID() {
        return externalID;
    }

    public void setExternalID(String externalID) {
        this.externalID = externalID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isLeft() {
        return isLeft;
    }

    public void setLeft(boolean left) {
        isLeft = left;
    }

    public int getIsLeftId() {
        return isLeftId;
    }

    public void setIsLeftId(int isLeftId) {
        this.isLeftId = isLeftId;
    }

    @Override
    public String toString() {
        return "{" +
                "\"apiKey\":" + apiKey +
                ",\"chatBotID\":" + chatBotID +
                ",\"externalID\":" + externalID +
                ",\"message\":" + message +
                ",\"firstName\":" + firstName +
                ",\"lastName\":" + lastName +
                ",\"gender\":" + gender +
                ",\"isLeft\":" + isLeftId +
                '}';
    }
}
