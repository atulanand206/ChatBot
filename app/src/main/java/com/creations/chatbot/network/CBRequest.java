package com.creations.chatbot.network;

import android.support.annotation.Nullable;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonRequest;
import com.creations.chatbot.callbacks.CompletionListener;
import com.creations.chatbot.utils.JsonConvertor;

class CBRequest<T> extends JsonRequest<T> {

    private VolleyManager volleyManager;
    private final Class<T> respClass;
    private Object requestBody;
    private Response.Listener listener;
    private Response.ErrorListener errorListener;
    private RetryPolicy retryPolicy;

    public <P> CBRequest(VolleyManager volleyManager, int method, String url, P requestBody,
                         Response.Listener<T> listener, @Nullable Response.ErrorListener errorListener, Class<T> respClass) {
        super(method, url, JsonConvertor.toJson(requestBody), listener, errorListener);
        this.volleyManager = volleyManager;
        this.requestBody = requestBody;
        this.listener = listener;
        this.errorListener = errorListener;
        this.respClass = respClass;
        this.retryPolicy = new RefreshPolicy(volleyManager, this, new CompletionListener() {
            @Override
            public void onSuccess() {
                listener.onResponse(null);
            }

            @Override
            public void onError(VolleyError error) {
                errorListener.onErrorResponse(error);
            }
        });
        this.setRetryPolicy(retryPolicy);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
            T resp = JsonConvertor.fromJson(jsonString, this.respClass);

            return Response.success(resp,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public void deliverError(VolleyError error) {
        try {
            if(error.networkResponse.statusCode==301) {
                retryPolicy.retry(new ServerError(error.networkResponse));
            }
        } catch (VolleyError error1) {
            error1.printStackTrace();
        }
        super.deliverError(error);
    }

    @Override
    protected CBRequest<T> clone() {
        return new CBRequest<>(volleyManager, getMethod(), getUrl(), requestBody, listener, errorListener, respClass);
    }

    public CBRequest<T> clone(String url) {
        return new CBRequest<T>(volleyManager, getMethod(), url, requestBody, listener, errorListener, respClass);
    }
}
