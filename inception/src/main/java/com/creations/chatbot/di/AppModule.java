package com.creations.chatbot.di;

import android.content.Context;

import com.creations.chatbot.ChatBotApplication;
import com.example.dagger.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @AppScope
    @Provides
    public static Context provideApplicationContext(ChatBotApplication application) {
        return application.getApplicationContext();
    }
}
