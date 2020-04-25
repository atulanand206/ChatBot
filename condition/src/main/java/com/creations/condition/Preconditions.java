package com.creations.condition;

import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Preconditions {

    /**
     * Verify that the possibly null object is not null. Use as a precondition when you want to statically
     * verify that object is not null.
     * @param object The object to ensure isn't null.
     * @param message The output message if it is null.
     * @param <T> The type of the object to check.
     * @return object if and only if it is not null.
     */
    @NonNull
    public static <T> T requiresNonNull(@NonNull final T object, @NonNull final String message) {
        //noinspection ConstantConditions
        if (object == null) {
            throw new PreconditionFailedException(message, new NullPointerException());
        } else {
            return object;
        }
    }

    /**
     * Verify that the possibly null object is not null. Use when you can't statically verify the
     * object is not null but need to throw an exception if it is null
     * @param object The object to ensure isn't null.
     * @param message The output message if it is null.
     * @param <T> The type of the object to check.
     * @return object if and only if it is not null.
     */
    @NonNull
    public static <T> T verifyNonNull(@Nullable final T object, @NonNull final String message) {
        if (object == null) {
            throw new PreconditionFailedException(message, new NullPointerException());
        } else {
            return object;
        }
    }

    @NonNull
    public static <T extends CharSequence> T verifyNonEmpty(@Nullable final T str, @NonNull final String message) {
        if (TextUtils.isEmpty(str)) {
            throw new PreconditionFailedException(message, new NullPointerException());
        } else {
            return str;
        }
    }

    @NonNull
    public static <T, U> U verifyInstanceOf(@Nullable final T object,
                                            @NonNull final Class<U> klass,
                                            @NonNull final String message) {
        Preconditions.verifyNonNull(klass, "Klass");

        verify(klass.isInstance(object), message);

        //noinspection ConstantConditions
        return klass.cast(object);
    }

    @SuppressWarnings("UnusedReturnValue")
    public static boolean verify(final boolean condition, @NonNull final String message) {
        if (!condition) {
            throw new PreconditionFailedException(message);
        } else {
            //noinspection ConstantConditions
            return condition;
        }
    }

    @NonNull
    public static String verifyStringLengthInclusive(@NonNull final String string, final int maxLength,
                                                     @NonNull final String message) {
        Preconditions.requiresNonNull(string, "String");

        String trimmed = string.trim();
        Preconditions.verifyNonEmpty(string, message);
        Preconditions.verify(string.equals(trimmed), message);
        Preconditions.verify(string.length() <= maxLength, message);
        return string;
    }

    @NonNull
    public static String verifyMatches(@NonNull final String string, @NonNull final String regex,
                                       @NonNull final String message) {
        Preconditions.requiresNonNull(string, "String");
        Preconditions.requiresNonNull(regex, "Regex");

        Preconditions.verify(string.matches(regex), message);
        return string;
    }

}
