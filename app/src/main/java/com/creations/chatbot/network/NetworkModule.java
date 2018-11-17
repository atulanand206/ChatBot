package com.creations.chatbot.network;

import android.content.Context;

import com.creations.chatbot.di.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @AppScope @Provides
    public static NetworkManager provideNetworkManager(Context context) {
        return new VolleyManager(context);
    }
}
