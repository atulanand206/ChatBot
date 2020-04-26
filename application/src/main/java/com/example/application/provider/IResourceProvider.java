package com.example.application.provider;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;
import androidx.annotation.IntegerRes;
import androidx.annotation.StringRes;

/**
 * Interface to access app's resources.
 */
public interface IResourceProvider {
    String getString(@StringRes int stringResId, Object... args);

    int getColor(@ColorRes int colorResId);

    float getDimen(@DimenRes int dimenResId);

    int getInteger(@IntegerRes int integerResId);
}
