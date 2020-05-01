package com.creations.inception.di;

import com.creations.inception.data.BloggerRepo;
import com.creations.inception.data.ChatRepository;
import com.creations.mvvm.constants.IAPIChat;
import com.example.dagger.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides @AppScope
    public static ChatRepository provideMainRepository(IAPIChat apiChat) {
        return new ChatRepository(apiChat);
    }

    @Provides @AppScope
    public static BloggerRepo provideBloggerRepo(IAPIChat apiChat) {
        return new BloggerRepo(apiChat);
    }

}
