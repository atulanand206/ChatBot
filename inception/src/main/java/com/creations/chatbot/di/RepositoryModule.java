package com.creations.chatbot.di;

import com.creations.chatbot.data.ChatRepository;
import com.creations.chatbot.network.IAPIChat;
import com.example.dagger.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides @AppScope
    public static ChatRepository provideMainRepository(IAPIChat apiChat) {
        return new ChatRepository(apiChat);
    }
}
