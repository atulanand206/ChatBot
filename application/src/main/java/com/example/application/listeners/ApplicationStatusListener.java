package com.example.application.listeners;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

public class ApplicationStatusListener implements LifecycleObserver {
    private static final String TAG = ApplicationStatusListener.class.getSimpleName();

    // initialize this value to true because a push notification might
    // come when the process is not alive, which means the app is in background state
    private boolean isAppInBackground = true;

    public ApplicationStatusListener(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(this);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    private void onAppForegrounded() {
        Log.v(TAG, "onAppForegrounded");
        isAppInBackground = false;
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    private void onAppBackgrounded() {
        Log.v(TAG, "onAppBackgrounded");
        isAppInBackground = true;
    }

    public boolean isAppInBackground() {
        Log.d(TAG, String.format("isAppInBackground: %s", isAppInBackground));
        return isAppInBackground;
    }
}
