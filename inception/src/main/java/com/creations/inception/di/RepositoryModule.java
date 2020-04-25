package com.creations.inception.di;

import com.creations.inception.data.ChatRepository;
import com.creations.inception.network.IAPIChat;
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
