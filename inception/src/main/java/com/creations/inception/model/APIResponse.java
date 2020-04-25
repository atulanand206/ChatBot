package com.creations.inception.model;

public class APIResponse {

    private int success;

    private String errorMessage;

    private Message message;

    public APIResponse(ListItem item) {
        this.success = 1;
        this.errorMessage = "";
        this.message = item.getMessage();
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
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
                "\"success\":" + success +
                ",\"errorMessage\":" + errorMessage +
                ",\"message\":" + message +
                '}';
    }
}
