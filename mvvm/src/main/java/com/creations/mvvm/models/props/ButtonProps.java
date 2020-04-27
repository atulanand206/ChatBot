package com.creations.mvvm.models.props;

import com.creations.condition.Preconditions;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class ButtonProps extends Props implements Serializable {

    private String message;

    public ButtonProps(@NonNull final String message) {
        this.message = Preconditions.requiresNonNull(message, "Message");
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(@NonNull final String message) {
        this.message = Preconditions.requiresNonNull(message, "Message");
    }
}
