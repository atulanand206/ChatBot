package com.creations.inception.utils;

import android.os.Bundle;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.navigation.NavigationBarProps;

import androidx.annotation.Nullable;

import static com.creations.condition.Helper.verifyBundleEntry;

public interface BundleHelper {

    String ARG_NAVIGATION_PROPS = "NAVIGATION_PROPS";

    String MSG_NAVIGATION_PROPS = "ArgsIsNavigationProps";

    @Nullable
    static NavigationBarProps getNavigationProps(@Nullable final Bundle arguments) {
        if (verifyBundleEntry(arguments, ARG_NAVIGATION_PROPS))
            return Preconditions.verifyInstanceOf(arguments.getSerializable(ARG_NAVIGATION_PROPS),
                    NavigationBarProps.class, MSG_NAVIGATION_PROPS);
        return null;
    }

}
