package com.creations.chatbot;

import android.app.Activity;
import android.app.Application;

import com.creations.chatbot.di.DaggerAppComponent;
import com.creations.chatbot.network.ConnectivityReceiver;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class ChatBotApplication extends Application implements HasActivityInjector {

    private static ChatBotApplication mInstance;

    @Inject DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
//        Realm.deleteRealm(configuration);
        Realm.setDefaultConfiguration(configuration);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    public static synchronized ChatBotApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }
}
