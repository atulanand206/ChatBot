package com.creations.inception;

import android.app.Activity;
import android.app.Application;

import com.creations.inception.di.DaggerAppComponent;
import com.google.firebase.analytics.FirebaseAnalytics;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends Application implements HasActivityInjector {

    private static App mInstance;

    @Inject DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    private FirebaseAnalytics mFirebaseAnalytics;


    @Override
    public void onCreate() {
        super.onCreate();

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        
        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
//        Realm.deleteRealm(configuration);
        Realm.setDefaultConfiguration(configuration);

        mInstance = this;
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    public FirebaseAnalytics getFirebaseAnalytics() {
        return mFirebaseAnalytics;
    }

    public static synchronized App getInstance() {
        return mInstance;
    }
}
