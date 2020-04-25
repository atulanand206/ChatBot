package com.example.application.messages;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;

public interface IMessageManager {
    /**
     * Displays the message with given properties as Toast.
     * @param message the string to be shown as toast message.
     * @param messageType the {@link MessageType} of the information to be displayed.
     * @param duration the length of time for which the message is to be displayed.
     */
    void showToast(@NonNull final String message,
                   @NonNull final MessageType messageType,
                   final @ToastUtils.Duration int duration);

    /**
     * Displays the message with given properties as Toast.
     * @param message the string to be shown as toast message.
     * @param messageType the {@link MessageType} of the information to be displayed.
     * @param duration the length of time for which the message is to be displayed.
     */
    void showToast(@StringRes final int stringResId,
                   @NonNull final MessageType messageType,
                   final @ToastUtils.Duration int duration);

    /**
     * Displays the message with given properties as Snackbar.
     * @param view the view in which snackbar is to be shown.
     * @param message the string to be shown as toast message.
     * @param messageType the {@link MessageType} of the information to be displayed.
     */
    void showSnackBar(@NonNull final View view,
                      @NonNull final String message,
                      @NonNull final MessageType messageType);

    /**
     * Displays the message with given properties as Snackbar.
     * @param view the view in which snackbar is to be shown.
     * @param message the string to be shown as toast message.
     * @param messageType the {@link MessageType} of the information to be displayed.
     */
    void showSnackBar(@NonNull final View view,
                      @StringRes final int stringResId,
                      @NonNull final MessageType messageType);

    /**
     * Shows the previous snackbar on screen.
     * @param view the view in which snackbar is to be shown.
     */
    void showLastSnackBar(@NonNull final View view);

    /**
     * Hides the snackbar if visible.
     */
    void hideSnackBar();
}
