package com.creations.tools.network;

import java.util.Objects;

import androidx.annotation.NonNull;

public class NetworkAuthError extends Exception {
    public NetworkAuthError(@NonNull String message) {
        super(message);

        Objects.requireNonNull(message, "Message");
    }

    public NetworkAuthError(@NonNull String message, @NonNull Throwable cause) {
        super(message, cause);

        Objects.requireNonNull(message, "Message");
        Objects.requireNonNull(cause, "Cause");
    }
}
