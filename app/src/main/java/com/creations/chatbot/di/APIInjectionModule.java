package com.creations.chatbot.di;

import com.creations.chatbot.di.scopes.AppScope;
import com.creations.chatbot.network.APIChat;
import com.creations.chatbot.network.IAPIChat;
import com.creations.chatbot.network.NetworkManager;

import dagger.Module;
import dagger.Provides;

@Module
public class APIInjectionModule {

    @Provides @AppScope
    public static IAPIChat providesAPIChats(NetworkManager networkManager) {
        return new APIChat(networkManager);
    }
}
