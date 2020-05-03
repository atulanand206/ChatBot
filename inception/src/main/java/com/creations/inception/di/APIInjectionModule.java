package com.creations.inception.di;

import com.creations.blocks.api.IAPIBlocks;
import com.creations.blogger.api.IAPIBlogger;
import com.creations.inception.network.APIBlocks;
import com.creations.inception.network.APIBlogger;
import com.creations.tools.network.NetworkManager;
import com.example.dagger.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class APIInjectionModule {

    @Provides @AppScope
    public static IAPIBlocks providesAPIChats(NetworkManager networkManager) {
        return new APIBlocks(networkManager);
    }

    @Provides @AppScope
    public static IAPIBlogger prrovideBlogger(NetworkManager networkManager) {
        return new APIBlogger(networkManager);
    }
}
