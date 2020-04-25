package com.creations.tools.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;

import androidx.annotation.NonNull;

public class MainThreadExecutor implements Executor {
    private final Handler mainThreadHandler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(@NonNull Runnable command) {
        mainThreadHandler.post(command);
    }
}
