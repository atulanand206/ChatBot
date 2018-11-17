package com.creations.chatbot.network;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.creations.chatbot.callbacks.ListResponseCallback;
import com.creations.chatbot.callbacks.ObjectResponseCallback;
import com.creations.chatbot.constants.AppConstants;
import com.creations.chatbot.model.Request;
import com.creations.chatbot.utils.JsonConvertor;

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

    private <T> void addToRequestQueue(@NonNull CBRequest<T> request, String tag) {
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
                                      @NonNull Class<T> classOfT) {
        Objects.requireNonNull(method);
        Objects.requireNonNull(url);
        Objects.requireNonNull(callback);
        Objects.requireNonNull(classOfT);

        String uri = buildRequest(url, requestBody);


        CBRequest<T> request = new CBRequest<T>(method, uri, null, new Response.Listener<T>() {
            @Override
            public void onResponse(T response) {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "onError: with status code " + error.networkResponse.statusCode, error);
                String errorMessage;
                Log.d(TAG, "onError: " + Arrays.toString(error.networkResponse.data));
                try {
                    Log.d(TAG, "onError: " + Arrays.toString(error.networkResponse.data));
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
                callback.onError(error.networkResponse.statusCode,errorMessage);
            }
        }, classOfT);

        mRequestQueue.add(request);
    }

    private String buildRequest(String url, Object requestBody) {
        Uri uri = Uri.parse(url).buildUpon()
                .appendQueryParameter(AppConstants.Request.API_KEY, AppConstants.API_KEY)
                .appendQueryParameter(AppConstants.Request.CHAT_BOT_ID, AppConstants.CHAT_BOT_ID)
                .appendQueryParameter(AppConstants.Request.EXTERNAL_ID, ((Request) requestBody).getExternalID())
                .appendQueryParameter(AppConstants.Request.MESSAGE, ((Request) requestBody).getMessage())
                .build();
        return uri.toString();
    }

    @Override
    public <T> void makeListRequest(@NonNull int method,
                                    @NonNull String url, @Nullable Object requestBody,
                                    @NonNull ListResponseCallback<T> callback,
                                    @NonNull Class<T> classOfT) {
        Objects.requireNonNull(method);
        Objects.requireNonNull(url);
        Objects.requireNonNull(callback);
        Objects.requireNonNull(classOfT);
    }

    private class CBRequest<T> extends JsonRequest<T> {

        private final Class<T> respClass;

        private String requestBody;

        private Response.Listener<T> listener;

        private Response.ErrorListener errorListener;

        public CBRequest(int method, String url, @Nullable String requestBody,
                         Response.Listener<T> listener, @Nullable Response.ErrorListener errorListener, Class<T> respClass) {
            super(method, url, requestBody, listener, errorListener);
            this.requestBody = requestBody;
            this.listener = listener;
            this.errorListener = errorListener;
            this.respClass = respClass;

        }

        @Override
        protected Response<T> parseNetworkResponse(NetworkResponse response) {
            try {
                String jsonString = new String(response.data,
                        HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
                T resp;
                resp = (T) JsonConvertor.fromJson(jsonString, this.respClass);

                return Response.success(resp,
                        HttpHeaderParser.parseCacheHeaders(response));
            } catch (Exception e) {
                return Response.error(new ParseError(e));
            }
        }
    }
}
