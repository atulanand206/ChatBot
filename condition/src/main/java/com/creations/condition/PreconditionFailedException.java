package com.creations.condition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class PreconditionFailedException extends RuntimeException {

    public PreconditionFailedException(@NonNull final String message) {
        super(message);
    }

    public PreconditionFailedException(@NonNull final String message,
                                       @Nullable final Exception source) {
        super(message, source);
    }

}
