package com.creations.inception.di;

import com.creations.blogger.api.IAPIBlogger;
import com.creations.inception.data.ChatRepository;
import com.example.dagger.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Provides @AppScope
    public static ChatRepository provideMainRepository(IAPIBlogger apiChat) {
        return new ChatRepository(apiChat);
    }

}
