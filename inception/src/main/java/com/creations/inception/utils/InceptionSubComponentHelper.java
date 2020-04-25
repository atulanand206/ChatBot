package com.creations.inception.utils;

import android.os.Bundle;

import com.creations.condition.Helper;
import com.creations.condition.Info;
import com.creations.inception.ui.form.RequestModule;
import com.creations.mvvm.models.navigation.NavigationBarProps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface InceptionSubComponentHelper {

    static void setInfo(@Nullable final Bundle arguments,
                        @NonNull final RequestModule.RequestSubcomponent.Builder builder) {
        Info info = Helper.getInfo(arguments);
        if (info != null)
            builder.info(info);
    }

    static void setNavigationProps(@Nullable final Bundle arguments,
                                   @NonNull final RequestModule.RequestSubcomponent.Builder builder) {
        NavigationBarProps navigationProps = BundleHelper.getNavigationProps(arguments);
        if (navigationProps != null)
            builder.navigationProps(navigationProps);
    }
}
