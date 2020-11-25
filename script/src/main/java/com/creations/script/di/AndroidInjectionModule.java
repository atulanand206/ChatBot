package com.creations.script.di;

import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

import com.creations.script.App;
import com.example.application.messages.IMessageManager;
import com.example.application.messages.MessageManager;
import com.example.application.messages.SnackbarUtils;
import com.example.application.messages.ToastUtils;
import com.example.application.provider.IResourceProvider;
import com.example.application.provider.ResourceProvider;
import com.example.application.utils.Animations;
import com.example.application.utils.NavigationDrawer;
import com.example.application.utils.SharedPreferenceHelper;
import com.example.dagger.scopes.AppScope;

import dagger.Module;
import dagger.Provides;

@Module
public class AndroidInjectionModule {

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
    public static NavigationDrawer provideNavigationDrawer() {
        return new NavigationDrawer();
    }

    @AppScope @Provides
    public static Animations provideAnimator() {
        return new Animations();
    }

    @AppScope @Provides
    public static SharedPreferenceHelper preferenceHelper(Context context) {
        try {
            return new SharedPreferenceHelper(context, String.valueOf(context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0).versionCode));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
