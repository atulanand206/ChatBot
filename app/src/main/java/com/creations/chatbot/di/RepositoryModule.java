package com.creations.chatbot.di;

import com.creations.chatbot.di.scopes.AppScope;
import com.creations.chatbot.network.IAPIChat;
import com.creations.chatbot.ui.MainRepository;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides @AppScope
    public static MainRepository provideMainRepository(IAPIChat apiChat) {
        return new MainRepository(apiChat);
    }
}
