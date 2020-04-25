package com.creations.inception.di;

import android.content.Context;

import com.creations.inception.App;
import com.example.dagger.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @AppScope
    @Provides
    public static Context provideApplicationContext(App application) {
        return application.getApplicationContext();
    }
}
