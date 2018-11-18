package com.creations.chatbot.network;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.Volley;
import com.creations.chatbot.callbacks.ObjectResponseCallback;
import com.creations.chatbot.constants.AppConstants;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

public class VolleyManager extends NetworkManager {

    private static final String TAG = VolleyManager.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private Context mContext;

    public VolleyManager(Context mContext) {
        this.mContext = mContext.getApplicationContext();
        mRequestQueue = Volley.newRequestQueue(this.mContext);
    }

    public <T> void addToRequestQueue(@NonNull CBRequest<T> request, String tag) {
        Log.d(TAG, "addToRequestQueue: " + request.getUrl());
        request.setTag(tag == null ? AppConstants.DEFAULT_TAG : tag);
        mRequestQueue.add(request);
    }

    public void cancelPendingRequests(@NonNull Object tag) {
        mRequestQueue.cancelAll(tag);
    }

    public void cancelAll() {
        mRequestQueue.cancelAll(request -> true);
    }

    @Override
    public <T> void makeObjectRequest(@NonNull int method,
                                      @NonNull String url, @Nullable Object requestBody,
                                      @NonNull ObjectResponseCallback<T> callback,
                                      @NonNull Class<T> classOfT,
                                      @NonNull String tag) {
        Objects.requireNonNull(method);
        Objects.requireNonNull(url);
        Objects.requireNonNull(callback);
        Objects.requireNonNull(classOfT);


        CBRequest<T> request = new CBRequest<>(this, method, url, requestBody,
                response -> {
                    Log.d(TAG, String.valueOf(response));
                    callback.onSuccess(response);
                },
                error -> {
                    int statusCode = 500;
                    String errorMessage = "Error is null";
                    if(error.networkResponse!=null) {
                        try {
                            Log.e(TAG, "onError: with status code " + error.networkResponse.statusCode, error);
                            Log.e(TAG, "onError: " + Arrays.toString(error.networkResponse.data));
                            statusCode = error.networkResponse.statusCode;
                            byte[] data = error.networkResponse.data;
                            Map<String, String> headers = error.networkResponse.headers;
                            String jsonString = new String(data,
                                    HttpHeaderParser.parseCharset(headers, "utf-8"));
                            errorMessage = jsonString;
                        } catch (UnsupportedEncodingException e) {
                            Log.e(TAG,
                                    "onError: Failed to deserialize response", e);
                            errorMessage = e.getMessage();
                        }
                    }
                    callback.onError(statusCode, errorMessage);
                }, classOfT);

        addToRequestQueue(request,tag);
    }


}
