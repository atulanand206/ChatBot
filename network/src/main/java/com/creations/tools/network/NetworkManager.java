package com.creations.tools.network;

import com.creations.tools.callback.ListResponseCallback;
import com.creations.tools.callback.ObjectResponseCallback;

import java.util.Map;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Contract that handles networking.
 */
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
    public final <T> void makeObjectRequest(@NonNull @RequestMethod.HTTPRequestMethod String method,
                               @NonNull String url,
                               @Nullable Object requestBody,
                               @NonNull ObjectResponseCallback<T> callback,
                               @NonNull Class<T> classOfT) {
        Objects.requireNonNull(method, "Method");
        Objects.requireNonNull(url, "Url");
        Objects.requireNonNull(callback, "Callback");
        Objects.requireNonNull(classOfT, "ClassOfT");

        makeObjectRequest(method, url, requestBody, callback, classOfT, ContentType.JSON);
    }

    /**
     * Make a JSON object request with FORM_ENCODED as the content type.
     * @param method request method.
     * @param url url of the request.
     * @param requestBody the request body, can be null if its a GET/HEAD request.
     * @param callback to get notified of the api call.
     * @param classOfT class of the expected response type.
     * @param <T> expected response type.
     */
    public final <T> void makeObjectRequestEncoded(@NonNull @RequestMethod.HTTPRequestMethod String method,
                                            @NonNull String url,
                                            @Nullable Object requestBody,
                                            @NonNull ObjectResponseCallback<T> callback,
                                            @NonNull Class<T> classOfT) {
        Objects.requireNonNull(method, "Method");
        Objects.requireNonNull(url, "Url");
        Objects.requireNonNull(callback, "Callback");
        Objects.requireNonNull(classOfT, "ClassOfT");

        makeObjectRequest(method, url, requestBody, callback, classOfT, ContentType.FORM_ENCODED);
    }

    /**
     * Make an object request of type {@param contentType}.
     * @param method request method.
     * @param url url of the request.
     * @param requestBody the request body, can be null if its a GET/HEAD request.
     * @param callback to get notified of the api call.
     * @param classOfT class of the expected response type.
     * @param contentType content type of the request.
     * @param <T> expected response type.
     */
    public abstract <T> void makeObjectRequest(@NonNull @RequestMethod.HTTPRequestMethod String method,
                                               @NonNull String url,
                                               @Nullable Object requestBody,
                                               @NonNull ObjectResponseCallback<T> callback,
                                               @NonNull Class<T> classOfT,
                                               @NonNull ContentType contentType);

    /**
     * Make a JSON object request with JSON as the content type.
     * @param method request method.
     * @param url url of the request.
     * @param requestBody the request body, can be null if its a GET/HEAD request.
     * @param callback to get notified of the api call with headers.
     * @param classOfT class of the expected response type.
     * @param headers a map with request specific headers.
     * @param <T> expected response type.
     */
    public final <T> void makeObjectRequestWithHeaders(@NonNull @RequestMethod.HTTPRequestMethod String method,
                                                       @NonNull String url,
                                                       @Nullable Object requestBody,
                                                       @NonNull ObjectResponseCallback<T> callback,
                                                       @NonNull Class<T> classOfT,
                                                       @NonNull Map<String, String> headers) {
        Objects.requireNonNull(method, "Method");
        Objects.requireNonNull(url, "Url");
        Objects.requireNonNull(callback, "Callback");
        Objects.requireNonNull(classOfT, "ClassOfT");
        Objects.requireNonNull(headers, "Headers");

        makeObjectRequestWithHeaders(method, url, requestBody, callback, classOfT, ContentType.JSON, headers);
    }

    /**
     * Make a JSON object request with FORM_ENCODED as the content type.
     * @param method request method.
     * @param url url of the request.
     * @param requestBody the request body, can be null if its a GET/HEAD request.
     * @param callback to get notified of the api call with headers.
     * @param classOfT class of the expected response type.
     * @param headers a map with request specific headers.
     * @param <T> expected response type.
     */
    public final <T> void makeObjectRequestEncodedWithHeaders(@NonNull @RequestMethod.HTTPRequestMethod String method,
                                                       @NonNull String url,
                                                       @Nullable Object requestBody,
                                                       @NonNull ObjectResponseCallback<T> callback,
                                                       @NonNull Class<T> classOfT,
                                                       @NonNull Map<String, String> headers) {
        Objects.requireNonNull(method, "Method");
        Objects.requireNonNull(url, "Url");
        Objects.requireNonNull(callback, "Callback");
        Objects.requireNonNull(classOfT, "ClassOfT");
        Objects.requireNonNull(headers, "Headers");

        makeObjectRequestWithHeaders(method, url, requestBody, callback, classOfT, ContentType.FORM_ENCODED, headers);
    }

    /**
     * Make an object request of type {@param contentType}.
     * @param method request method.
     * @param url url of the request.
     * @param requestBody the request body, can be null if its a GET/HEAD request.
     * @param callback to get notified of the api call with headers.
     * @param classOfT class of the expected response type.
     * @param contentType content type of the request.
     * @param headers a map with request specific headers.
     * @param <T> expected response type.
     */
    public abstract <T> void makeObjectRequestWithHeaders(@NonNull @RequestMethod.HTTPRequestMethod String method,
                                                          @NonNull String url,
                                                          @Nullable Object requestBody,
                                                          @NonNull ObjectResponseCallback<T> callback,
                                                          @NonNull Class<T> classOfT,
                                                          @NonNull ContentType contentType,
                                                          @NonNull Map<String, String> headers);

    /**
     * Make a JSON array request with JSON as the content type.
     * @param method request method.
     * @param url url of the request.
     * @param requestBody the request body, can be null if its a GET/HEAD request.
     * @param callback to get notified of the api call.
     * @param classOfT class of the expected response type.
     * @param <T> expected response type.
     */
    public final <T> void makeListRequest(@NonNull @RequestMethod.HTTPRequestMethod String method,
                             @NonNull String url,
                             @Nullable Object requestBody,
                             @NonNull ListResponseCallback<T> callback,
                             @NonNull Class<T> classOfT) {
        Objects.requireNonNull(method, "Method");
        Objects.requireNonNull(url, "Url");
        Objects.requireNonNull(callback, "Callback");
        Objects.requireNonNull(classOfT, "ClassOfT");

        makeListRequest(method, url, requestBody, callback, classOfT, ContentType.JSON);
    }

    /**
     * Make an object request of type {@param contentType}.
     * @param method request method.
     * @param url url of the request.
     * @param requestBody the request body, can be null if its a GET/HEAD request.
     * @param callback to get notified of the api call.
     * @param classOfT class of the expected response type.
     * @param contentType content type of the request.
     * @param <T> expected response type.
     */
    public abstract <T> void makeListRequest(@NonNull @RequestMethod.HTTPRequestMethod String method,
                             @NonNull String url,
                             @Nullable Object requestBody,
                             @NonNull ListResponseCallback<T> callback,
                             @NonNull Class<T> classOfT,
                             @NonNull ContentType contentType);
}
