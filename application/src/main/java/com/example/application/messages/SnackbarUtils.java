package com.example.application.messages;

import android.content.Context;
import android.view.View;

import com.example.application.R;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import androidx.annotation.NonNull;

/**
 * Handles long duration messages as Snackbar.
 */
public class SnackbarUtils {

    private final Context context;

    private Snackbar snackbar;
    private String message;
    private MessageType messageType;
    private int messageLength;

    public SnackbarUtils(@NonNull final Context context) {
        Objects.requireNonNull(context, "context");

        this.context = context;
        this.messageType = MessageType.INFO;
        this.messageLength = Snackbar.LENGTH_INDEFINITE;
    }

    /**
     * The {@link Snackbar} will show up indefinitely, can be dismissed by swiping.
     * The default type of Snackbar is {@link MessageType#INFO} and will come up in black color.
     * @param view the view on which the Snackbar to display.
     * @param stringResId the resId for the string to be shown in Snackbar.
     * @param messageType message type currently used to set the background color of the Snackbar.
     */
    public void show(@NonNull final View view, final int stringResId,
                     @NonNull final MessageType messageType) {
        Objects.requireNonNull(view, "view");
        Objects.requireNonNull(messageType,"messageType");

        this.messageType = messageType;
        this.message = context.getString(stringResId);
        show(view);
    }

    /**
     * The {@link Snackbar} will show up indefinitely, can be dismissed by swiping.
     * The default type of Snackbar is {@link MessageType#INFO} and will come up in black color.
     * @param view the view on which the Snackbar to display.
     * @param message the string to be shown in Snackbar.
     * @param messageType message type currently used to set the background color of the Snackbar.
     */
    public void show(@NonNull final View view, @NonNull final String message,
                     @NonNull final MessageType messageType) {
        Objects.requireNonNull(view, "view");
        Objects.requireNonNull(message,"message");
        Objects.requireNonNull(messageType,"messageType");

        this.message = message;
        this.messageType = messageType;
        show(view);
    }

    /**
     * The {@link Snackbar} will show up indefinitely, can be dismissed by swiping.
     * The default type of Snackbar is {@link MessageType#INFO} and will come up in black color.
     * @param view the view on which the Snackbar to display.
     * @param message the string to be shown in Snackbar.
     * @param messageType message type currently used to set the background color of the Snackbar.
     * @param messageLength
     */
    public void show(@NonNull final View view, @NonNull final String message,
                     @NonNull final MessageType messageType, final int messageLength) {
        Objects.requireNonNull(view, "view");
        Objects.requireNonNull(message,"message");
        Objects.requireNonNull(messageType,"messageType");

        this.message = message;
        this.messageType = messageType;
        this.messageLength = messageLength;
        show(view);
    }

    /**
     * The {@link Snackbar} will show up indefinitely, can be dismissed by swiping.
     * The default type of Snackbar is {@link MessageType#INFO} and will come up in black color.
     * @param view the view on which the Snackbar to display.
     * @param stringResId the resId for the string to be shown in Snackbar.
     */
    public void show(@NonNull final View view, final int stringResId) {
        Objects.requireNonNull(view, "view");

        this.message = context.getString(stringResId);
        show(view);
    }

    /**
     * Uses the already set message string, type and length to show the Snackbar.
     * @param view the view on which the {@link Snackbar} to display.
     */
    public void show(@NonNull final View view) {
        Objects.requireNonNull(view, "view");
        snackbar = Snackbar.make(view, message, messageLength);
        View sbView = snackbar.getView();
        if (messageType == MessageType.INFO) {
            sbView.setBackgroundColor(context.getColor(R.color.message_progress));
        }
        snackbar.show();
    }

    /**
     * Call to dismiss the {@link Snackbar}.
     */
    public void stop() {
        if(snackbar != null && snackbar.isShown())
            snackbar.dismiss();
    }

    /**
     * @return the view height of {@link Snackbar}.
     */
    public int getHeight() {
        if(snackbar!=null)
            return snackbar.getView().getMeasuredHeight();
        return 0;
    }
}
