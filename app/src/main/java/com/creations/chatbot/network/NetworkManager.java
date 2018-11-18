package com.creations.chatbot.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.creations.chatbot.callbacks.ObjectResponseCallback;

public abstract class NetworkManager {

    /**
     * Make a JSON object request with JSON as the content type.
     * @param method request method.
     * @param url url of the request.
     * @param requestBody the request body, can be null if its a GET/HEAD request.
     * @param callback to get notified of the api call.
     * @param classOfT class of the expected response type.
     * @param <T> expected response type.
     */
    public abstract <T> void makeObjectRequest(@NonNull int method,
                           @NonNull String url,
                           @Nullable Object requestBody,
                           @NonNull ObjectResponseCallback<T> callback,
                           @NonNull Class<T> classOfT,
                           @NonNull String tag);

}
