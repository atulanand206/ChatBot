package com.example.application.messages;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

public class MessageManager implements IMessageManager {

    private final SnackbarUtils snackbarUtils;
    private final ToastUtils toastUtils;

    public MessageManager(@NonNull final SnackbarUtils snackbarUtils,
                           @NonNull final ToastUtils toastUtils) {
        this.snackbarUtils = snackbarUtils;
        this.toastUtils = toastUtils;
    }


    @Override
    public void showToast(@NonNull final String message,
                          @NonNull final MessageType messageType,
                          @ToastUtils.Duration final int duration) {
        toastUtils.showToast(message, messageType, duration, snackbarUtils.getHeight());
    }

    @Override
    public void showToast(@StringRes final int stringResId,
                          @NonNull final MessageType messageType,
                          @ToastUtils.Duration final int duration) {
        toastUtils.showToast(stringResId, messageType, duration, snackbarUtils.getHeight());
    }

    @Override
    public void showSnackBar(@NonNull final View view,
                             @NonNull final String message,
                             @NonNull final MessageType messageType) {
        snackbarUtils.show(view, message, messageType);
    }

    @Override
    public void showSnackBar(@NonNull final View view,
                             @StringRes final int stringResId,
                             @NonNull final MessageType messageType) {
        snackbarUtils.show(view, stringResId, messageType);
    }

    @Override
    public void showLastSnackBar(@NonNull final View view) {
        snackbarUtils.show(view);
    }

    @Override
    public void hideSnackBar() {
        snackbarUtils.stop();
    }

}

