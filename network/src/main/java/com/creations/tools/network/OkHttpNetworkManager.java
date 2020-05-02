package com.creations.tools.network;

import android.util.Log;

import com.creations.blogger.callback.ErrorResponseCallback;
import com.creations.blogger.callback.ListResponseCallback;
import com.creations.blogger.callback.ObjectResponseCallback;
import com.creations.blogger.model.APIResponseBody;
import com.creations.tools.NetworkConstants;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class OkHttpNetworkManager extends NetworkManager {
    private static final String TAG = OkHttpNetworkManager.class.getSimpleName();

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private final OkHttpClient client;
    private final Gson gson;
    private final Executor mainThreadExecutor;

    public OkHttpNetworkManager(@NonNull OkHttpClient okHttpClient, @NonNull Gson gson,
                                @NonNull Executor mainThreadExecutor) {
        Objects.requireNonNull(okHttpClient, "OkHttpClient");
        Objects.requireNonNull(gson, "Gson");


        this.client = okHttpClient;
        this.gson = gson;
        this.mainThreadExecutor = mainThreadExecutor;
    }

    private Request buildRequestObject(@NonNull String url, @NonNull String method, @Nullable Object requestObject,
                                       @NonNull ContentType contentType, @Nullable Map<String, String> headers) {
        Objects.requireNonNull(url, "Url");
        Objects.requireNonNull(method, "Method");
        Objects.requireNonNull(contentType, "ContentType");

        Request.Builder requestBuilder = new Request.Builder();
        if(headers!=null) {
            for (Map.Entry<String, String> entry : headers.entrySet())
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
        }
        RequestBody requestBody;
        if (contentType == ContentType.FORM_ENCODED) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            if(requestObject != null) {
                for (Field field : requestObject.getClass().getDeclaredFields()) {
                    field.setAccessible(true);
                    try {
                        // convert the field to snake case
                        String key = field.getName().replaceAll("([^_A-Z])([A-Z])", "$1_$2").toLowerCase();
                        Object value = field.get(requestObject);
                        formBodyBuilder.add(key, value == null ? "" : value.toString());
                    } catch (IllegalAccessException e) {
                        // ignore this exception and just don't add it to the request body
                        Log.e(TAG, String.format("Failed to add the field %s to request body", field.getName()), e);
                    }
                }
            }
            requestBody = formBodyBuilder.build();
            requestBuilder.addHeader(NetworkConstants.APIHeaders.CONTENT_TYPE,
                    NetworkConstants.APIHeaders.CONTENT_TYPE_FORM_URL_ENCODED);
        } else if (contentType == ContentType.DOCS) {
            requestBody = null;
        } else {
            requestBuilder.addHeader(NetworkConstants.APIHeaders.CONTENT_TYPE,
                    NetworkConstants.APIHeaders.CONTENT_TYPE_JSON);
            requestBody = requestObject == null ? null :
                    RequestBody.create(JSON, gson.toJson(requestObject));
        }
        Log.d(TAG, String.valueOf(requestBody));
        return requestBuilder
                .url(url)
                .method(method, requestBody)
                .build();
    }

    @Override
    public <T> void makeObjectRequest(@NonNull @RequestMethod.HTTPRequestMethod String method,
                                      @NonNull String url,
                                      @Nullable Object requestBody,
                                      @NonNull ObjectResponseCallback<T> callback,
                                      @NonNull Class<T> classOfT,
                                      @NonNull ContentType contentType) {
        Objects.requireNonNull(method, "Method");
        Objects.requireNonNull(url, "Url");
        Objects.requireNonNull(callback, "Callback");
        Objects.requireNonNull(classOfT, "ClassOfT");
        Objects.requireNonNull(contentType, "ContentType");

        Request request = buildRequestObject(url, method, requestBody, contentType, null);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Objects.requireNonNull(call, "Call");
                Objects.requireNonNull(e, "IOException");

                if (!call.isCanceled()) {
                    parseErrorResponse(0, null, callback);
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                Objects.requireNonNull(call, "Call");
                Objects.requireNonNull(response, "Response");

                ResponseBody body = response.body();
                if (response.isSuccessful()) {
                    if (body != null) {
                        try {
                            String responseString = body.string();
                            T result = gson.fromJson(responseString, classOfT);
                            runOnMainThread(() -> callback.onSuccess(result));
                        } catch (IOException|JsonParseException e) {
                            runOnMainThread(() -> callback.onError(0, "", new APIResponseBody(), e));
                        }
                    }
                } else {
                    parseErrorResponse(response.code(), body, callback);
                }
            }
        });
    }

    @Override
    public <T> void makeObjectRequestWithHeaders(@NonNull @RequestMethod.HTTPRequestMethod String method,
                                                 @NonNull String url,
                                                 @Nullable Object requestBody,
                                                 @NonNull ObjectResponseCallback<T> callback,
                                                 @NonNull Class<T> classOfT,
                                                 @NonNull ContentType contentType,
                                                 @NonNull Map<String, String> headers) {
        Objects.requireNonNull(method, "Method");
        Objects.requireNonNull(url, "Url");
        Objects.requireNonNull(callback, "Callback");
        Objects.requireNonNull(classOfT, "ClassOfT");
        Objects.requireNonNull(contentType, "ContentType");
        Objects.requireNonNull(headers, "Headers");

        Request request = buildRequestObject(url, method, requestBody, contentType, headers);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Objects.requireNonNull(call, "Call");
                Objects.requireNonNull(e, "IOException");

                if (!call.isCanceled()) {
                    parseErrorResponse(0, null, callback);
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                Objects.requireNonNull(call, "Call");
                Objects.requireNonNull(response, "Response");

                ResponseBody body = response.body();
                if (response.isSuccessful()) {
                    if (body != null) {
                        try {
                            String responseString = body.string();
                            T result = gson.fromJson(responseString, classOfT);
                            runOnMainThread(() -> callback.onSuccess(result));
                        } catch (IOException|JsonParseException e) {
                            runOnMainThread(() -> callback.onError(0, "", new APIResponseBody(), e));
                        }
                    }
                } else {
                    parseErrorResponse(response.code(), body, callback);
                }
            }
        });
    }

    @Override
    public <T> void makeListRequest(@NonNull @RequestMethod.HTTPRequestMethod String method,
                                    @NonNull String url,
                                    @Nullable Object requestBody,
                                    @NonNull ListResponseCallback<T> callback,
                                    @NonNull Class<T> classOfT,
                                    @NonNull ContentType contentType) {
        Objects.requireNonNull(method, "Method");
        Objects.requireNonNull(url, "Url");
        Objects.requireNonNull(callback, "Callback");
        Objects.requireNonNull(classOfT, "ClassOfT");
        Objects.requireNonNull(contentType, "ContentType");

        Request request = buildRequestObject(url, method, requestBody, contentType, null);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                Objects.requireNonNull(call, "Call");
                Objects.requireNonNull(e, "IOException");

                if (!call.isCanceled()) {
                    parseErrorResponse(0, null, callback);
                }
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                Objects.requireNonNull(call, "Call");
                Objects.requireNonNull(response, "Response");

                final List<T> results = new ArrayList<>();
                ResponseBody body = response.body();
                if (response.isSuccessful()) {
                    try {
                        if (body != null) {
                            String responseString = body.string();
                            JsonArray jsonArray = gson.fromJson(responseString, JsonArray.class);
                            if (jsonArray != null) {
                                for (JsonElement jsonElement : jsonArray) {
                                    results.add(gson.fromJson(jsonElement, classOfT));
                                }
                            }
                            runOnMainThread(() -> callback.onSuccess(results));
                        }
                    } catch (IOException|JsonParseException e) {
                        runOnMainThread(() -> callback.onError(0, "", new APIResponseBody(), e));
                    }
                } else {
                    parseErrorResponse(response.code(), body, callback);
                }
            }
        });
    }

    private void parseErrorResponse(int statusCode, @Nullable ResponseBody body, @NonNull ErrorResponseCallback callback) {
        Objects.requireNonNull(callback, "ErrorResponseCallback");

        APIResponseBody apiResponseBody = new APIResponseBody();
        Exception e = null;
        String errorResponseString = "";
        try {
            if (body != null) {
                errorResponseString = body.string();
                apiResponseBody = gson.fromJson(errorResponseString, APIResponseBody.class);
            }
        } catch (JsonParseException|IOException error) {
            e = error;
        } finally {
            String finalErrorResponseString = errorResponseString;
            APIResponseBody finalApiResponseBody = apiResponseBody;
            Exception finalE = e;
            runOnMainThread(() -> callback.onError(statusCode, finalErrorResponseString, finalApiResponseBody, finalE));
        }
    }

    private void runOnMainThread(@NonNull Runnable runnable) {
        Objects.requireNonNull(runnable, "Runnable");

        mainThreadExecutor.execute(runnable);
    }
}
