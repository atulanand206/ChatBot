package com.creations.tools.network;

import android.util.Log;

import java.io.IOException;
import java.util.Objects;

import androidx.annotation.NonNull;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggingInterceptor implements Interceptor {
    private static final String TAG = LoggingInterceptor.class.getSimpleName();

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Objects.requireNonNull(chain, "Chain");
        
        Request request = chain.request();

        long t1 = System.nanoTime();
        Log.v(TAG, String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Log.v(TAG, String.format("[%s] Received response for %s in %.1fms%n%s",
                response.code(), response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }
}