package com.creations.mvvm.models.navigation;

import com.creations.mvvm.models.props.Props;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

import static com.creations.mvvm.models.navigation.NavigationLabel.ADVANCED;
import static com.creations.mvvm.models.navigation.NavigationLabel.EXPERT;
import static com.creations.mvvm.models.navigation.NavigationLabel.INTERMEDIATE;
import static com.creations.mvvm.models.navigation.NavigationLabel.NOVICE;
import static com.creations.mvvm.models.navigation.NavigationState.CURRENT;

/**
 * This class is to be used for creating the model for the text view in EditableViewModel.
 */
public class NavigationBarProps extends Props implements Serializable {

    private final List<NavigationItem> mItems = new ArrayList<>();

    public NavigationBarProps() {
    }

    public NavigationBarProps(@NonNull final List<NavigationItem> items) {
        mItems.addAll(items);
    }

    public List<NavigationItem> getItems() {
        return mItems;
    }

    public void setItems(@NonNull final List<NavigationItem> items) {
        mItems.clear();
        mItems.addAll(items);
    }

    public void addItem(@NonNull final NavigationItem navigationItem) {
        mItems.add(navigationItem);
    }

    public void setState(@NonNull final NavigationLabel label,
                          @NonNull final NavigationState state) {
        for (NavigationItem item : mItems)
            if (item.getLabel().equals(label))
                item.setNavigationState(state);
    }

    public NavigationState getState(@NonNull final NavigationLabel label) {
        for (NavigationItem item : mItems) {
            if (item.getLabel().equals(label))
                return item.getNavigationState();
        }
            return CURRENT;
    }

    public static class Builder {

        private NavigationBarProps mProps;

        public Builder() {
            mProps = new NavigationBarProps();
        }

        public Builder withState(@NonNull final NavigationLabel label,
                                 @NonNull final NavigationState state) {
            mProps.addItem(new NavigationItem(label, state));
            return this;
        }

        public Builder withNOVICE(@NonNull final NavigationState state) {
            return withState(NOVICE, state);
        }

        public Builder withINTERMEDIATE(@NonNull final NavigationState state) {
            return withState(INTERMEDIATE, state);
        }

        public Builder withADVANCED(@NonNull final NavigationState state) {
            return withState(ADVANCED, state);
        }

        public Builder withEXPERT(@NonNull final NavigationState state) {
            return withState(EXPERT, state);
        }

        public NavigationBarProps build() {
            return mProps;
        }
    }

}
