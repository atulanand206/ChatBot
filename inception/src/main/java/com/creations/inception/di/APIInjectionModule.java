package com.creations.inception.di;

import android.content.Context;

import com.creations.blogger.IAPIChat;
import com.creations.inception.network.APIChat;
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
