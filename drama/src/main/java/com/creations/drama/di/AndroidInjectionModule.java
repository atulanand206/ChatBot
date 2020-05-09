package com.creations.drama.di;

import android.content.Context;

import com.creations.drama.App;
import com.creations.drama.api.APICanvas;
import com.creations.drama.ui.MainActivity;
import com.creations.tools.network.NetworkManager;
import com.example.application.messages.IMessageManager;
import com.example.application.messages.MessageManager;
import com.example.application.messages.SnackbarUtils;
import com.example.application.messages.ToastUtils;
import com.example.application.provider.IResourceProvider;
import com.example.application.provider.ResourceProvider;
import com.example.dagger.scopes.ActivityScope;
import com.example.dagger.scopes.AppScope;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class AndroidInjectionModule {

    @ActivityScope
    @ContributesAndroidInjector()
    public abstract MainActivity contributeMainActivityInjector();

    @AppScope
    @Provides
    public static Context provideApplicationContext(App application) {
        return application.getApplicationContext();
    }

    @AppScope @Provides
    public static ToastUtils provideToastUtils(@NonNull final Context context) {
        return new ToastUtils(context);
    }

    @AppScope
    @Provides
    public static SnackbarUtils provideSnackbarUtils(@NonNull final Context context) {
        return new SnackbarUtils(context);
    }

    @AppScope @Provides
    public static IMessageManager provideMessageManager(@NonNull final ToastUtils toastUtils,
                                                        @NonNull final SnackbarUtils snackbarUtils) {
        return new MessageManager(snackbarUtils, toastUtils);
    }

    @AppScope @Provides
    public static IResourceProvider provideResourceProvider(@NonNull final Context context) {
        return new ResourceProvider(context);
    }

    @AppScope @Provides
    public static APICanvas provideAPICanvas(@NonNull final NetworkManager networkManager,
                                             @NonNull final IResourceProvider resourceProvider) {
        return new APICanvas();
    }
}
