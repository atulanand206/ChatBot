package com.creations.naina.di;

import com.creations.bang.api.IAPIBang;
import com.creations.naina.api.APICanvas;
import com.creations.tools.network.NetworkManager;
import com.example.application.provider.IResourceProvider;
import com.example.dagger.scopes.AppScope;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    @AppScope
    @Provides
    public static IAPIBang provideAPICanvas(@NonNull final NetworkManager networkManager,
                                            @NonNull final IResourceProvider resourceProvider) {
        return new APICanvas();
    }

}
