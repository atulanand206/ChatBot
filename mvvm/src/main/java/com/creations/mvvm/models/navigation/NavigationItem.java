package com.creations.mvvm.models.navigation;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.props.Props;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class NavigationItem extends Props implements Serializable {

    private final NavigationLabel mLabel;

    private NavigationState mNavigationState;

    public NavigationItem(@NonNull final NavigationLabel label,
                          @NonNull final NavigationState navigationState) {
        this.mLabel = Preconditions.requiresNonNull(label, "Label");
        this.mNavigationState = Preconditions.requiresNonNull(navigationState, "NavigationState");
    }

    public NavigationLabel getLabel() {
        return mLabel;
    }

    public NavigationState getNavigationState() {
        return mNavigationState;
    }

    public void setNavigationState(@NonNull final NavigationState navigationState) {
        this.mNavigationState = Preconditions.requiresNonNull(navigationState, "NavigationState");
    }
}
