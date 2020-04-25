package com.creations.inception.di;

import android.content.Context;

import com.example.application.messages.IMessageManager;
import com.example.application.messages.MessageManager;
import com.example.application.messages.SnackbarUtils;
import com.example.application.messages.ToastUtils;
import com.example.dagger.scopes.AppScope;

import androidx.annotation.NonNull;
import dagger.Module;
import dagger.Provides;

@Module
public class MessageModule {
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

}
