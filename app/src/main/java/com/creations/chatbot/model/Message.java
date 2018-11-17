package com.creations.chatbot.model;

import com.creations.chatbot.constants.AppConstants;

public class Message {

    private String chatBotName;

    private String chatBotID;

    private String message;

    private String emotion;

    public Message() {
        this.chatBotName = "ABC";
        this.chatBotID = "121";
        this.message = "What a lovely day";
        this.emotion = ":P";
    }

    public Message(String newEntry) {
        this.chatBotName = AppConstants.ChatBotName;
        this.chatBotID = AppConstants.ChatBotID;
        this.message = newEntry;
        this.emotion = ":P";
    }

    public String getChatBotName() {
        return chatBotName;
    }

    public void setChatBotName(String chatBotName) {
        this.chatBotName = chatBotName;
    }

    public String getChatBotID() {
        return chatBotID;
    }

    public void setChatBotID(String chatBotID) {
        this.chatBotID = chatBotID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    @Override
    public String toString() {
        return "{" +
                "\"chatBotName\":" + chatBotName +
                ",\"chatBotID\"" + chatBotID +
                ",\"message\":" + message +
                ",\"emotion\":" + emotion +
                '}';
    }
}
