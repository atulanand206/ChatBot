package com.creations.chatbot.network;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.creations.chatbot.callbacks.ListResponseCallback;
import com.creations.chatbot.callbacks.ObjectResponseCallback;

import java.util.Objects;

public class VolleyManager extends NetworkManager {

    @Override
    public <T> void makeObjectRequest(@NonNull String method,
                                      @NonNull String url, @Nullable Object requestBody,
                                      @NonNull ObjectResponseCallback<T> callback,
                                      @NonNull Class<T> classOfT) {
        Objects.requireNonNull(method);
        Objects.requireNonNull(url);
        Objects.requireNonNull(callback);
        Objects.requireNonNull(classOfT);
    }

    @Override
    public <T> void makeListRequest(@NonNull String method,
                                    @NonNull String url, @Nullable Object requestBody,
                                    @NonNull ListResponseCallback<T> callback,
                                    @NonNull Class<T> classOfT) {
        Objects.requireNonNull(method);
        Objects.requireNonNull(url);
        Objects.requireNonNull(callback);
        Objects.requireNonNull(classOfT);
    }
}
