package com.creations.inception.network;

import com.creations.blocks.models.Board;
import com.creations.blocks.utils.BoardSerializer;
import com.creations.tools.network.LoggingInterceptor;
import com.creations.tools.network.NetworkManager;
import com.creations.tools.network.OkHttpNetworkManager;
import com.creations.tools.network.SkipSerialisation;
import com.creations.tools.utils.JsonConvertor;
import com.creations.tools.utils.MainThreadExecutor;
import com.example.dagger.scopes.AppScope;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import androidx.annotation.MainThread;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class NetworkModule {

    @AppScope @Provides @MainThread
    public static Executor provideMainThreadExecutor() {
        return new MainThreadExecutor();
    }

    @AppScope
    @Provides
    public static OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder()
                .addNetworkInterceptor(new LoggingInterceptor())
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10_000, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
    }

    @AppScope
    @Provides
    public static Gson provideGson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Board.class, new BoardSerializer())
                .addSerializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getAnnotation(SkipSerialisation.class) != null;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
    }

    @AppScope
    @Provides
    public static NetworkManager provideNetworkManager(OkHttpClient okHttpClient,
                                                       Gson gson,
                                                       @MainThread Executor mainThreadExecutor) {
        return new OkHttpNetworkManager(okHttpClient, gson, mainThreadExecutor);
    }

    @AppScope
    @Provides
    public static JsonConvertor provideJsonConvertor(Gson gson) {
        return new JsonConvertor(gson);
    }
}
