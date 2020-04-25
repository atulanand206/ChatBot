package com.creations.condition;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface Helper {

    String ARG_INFO = "INFO";

    String MSG_INFO = "ArgsIsInfo";

    static boolean verifyBundleEntry(@Nullable final Bundle arguments,
                                     @NonNull final String key) {
        if (arguments == null)
            return false;
        return arguments.containsKey(key);
    }

    @Nullable
    static Info getInfo(@Nullable final Bundle arguments) {
        if (verifyBundleEntry(arguments, ARG_INFO))
            return Preconditions.verifyInstanceOf(arguments.getSerializable(ARG_INFO), Info.class, MSG_INFO);
        return null;
    }

}
