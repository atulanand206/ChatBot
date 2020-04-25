package com.creations.chatbot.di;

import android.content.Context;

import com.creations.chatbot.network.APIChat;
import com.creations.chatbot.network.IAPIChat;
import com.creations.tools.network.NetworkManager;
import com.example.dagger.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class APIInjectionModule {

    @Provides @AppScope
    public static IAPIChat providesAPIChats(Context context, NetworkManager networkManager) {
        return new APIChat(context, networkManager);
    }
}
