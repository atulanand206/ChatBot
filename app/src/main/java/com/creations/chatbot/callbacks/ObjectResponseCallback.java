package com.creations.chatbot.callbacks;

public interface ObjectResponseCallback<T> {

    void onSuccess(T response);

    void onError(int responseCode, String errorMessage);
}
