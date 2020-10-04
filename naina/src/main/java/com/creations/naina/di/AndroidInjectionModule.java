package com.creations.naina.di;

import android.content.Context;
import android.content.pm.PackageManager;

import com.creations.naina.App;
import com.creations.naina.api.ConfigurationRepository;
import com.creations.naina.api.IConfigurationRepository;
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
import com.google.gson.Gson;

import androidx.annotation.NonNull;
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

    @AppScope @Provides
    public static IConfigurationRepository configurationRepository(final SharedPreferenceHelper sharedPreferenceHelper,
                                                                   final Context context,
                                                                   final Gson gson) {
        return new ConfigurationRepository(sharedPreferenceHelper, context, gson);
    }
}
