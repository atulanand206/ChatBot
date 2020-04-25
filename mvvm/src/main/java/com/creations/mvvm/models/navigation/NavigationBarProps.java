package com.creations.mvvm.models.navigation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

import static com.creations.mvvm.models.navigation.NavigationLabel.Advisory;
import static com.creations.mvvm.models.navigation.NavigationLabel.Checklist;
import static com.creations.mvvm.models.navigation.NavigationLabel.NOTAM;
import static com.creations.mvvm.models.navigation.NavigationLabel.SGI;
import static com.creations.mvvm.models.navigation.NavigationState.CURRENT;
import static com.creations.mvvm.models.navigation.NavigationState.NOT_YET_OPENED;

/**
 * This class is to be used for creating the model for the text view in EditableViewModel.
 */
public class NavigationBarProps implements Serializable {

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

        public Builder withAdvisory(@NonNull final NavigationState state) {
            return withState(Advisory, state);
        }

        public Builder withSGI(@NonNull final NavigationState state) {
            return withState(SGI, state);
        }

        public Builder withNOTAM(@NonNull final NavigationState state) {
            return withState(NOTAM, state);
        }

        public Builder withChecklist(@NonNull final NavigationState state) {
            return withState(Checklist, state);
        }

        public NavigationBarProps build() {
            return mProps;
        }
    }

    public static NavigationBarProps defaultProps(final boolean sgiEnabled,
                                                  final boolean notamEnabled,
                                                  final boolean checklistEnabled) {
        Builder builder = new Builder().withAdvisory(CURRENT);
        if (sgiEnabled)
            builder = builder.withSGI(NOT_YET_OPENED);
        if (notamEnabled)
            builder = builder.withNOTAM(NOT_YET_OPENED);
        if (checklistEnabled)
            builder = builder.withChecklist(NOT_YET_OPENED);
        return builder.build();
    }

}
