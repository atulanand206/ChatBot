package com.creations.mvvm.ui.menu;

import android.app.Application;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.navigation.NavigationBarProps;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class MenuViewModel extends MVVMViewModel implements MenuContract.ViewModel {

    @NonNull
    private final NavigationBarProps mNavigationBarProps;

    public MenuViewModel(@NonNull final Application application,
                         @NonNull final NavigationBarProps navigationBarProps) {
        super(application);
        mNavigationBarProps = Preconditions.requiresNonNull(navigationBarProps, "NavigationBarProps");

    }

    public static class Factory extends MVVMViewModel.Factory<MenuViewModel> {

        @NonNull
        private final NavigationBarProps mNavigationBarProps;

        public Factory(@NonNull final Application application,
                       @NonNull final NavigationBarProps navigationBarProps) {
            super(MenuViewModel.class, application);
            mNavigationBarProps = Preconditions.requiresNonNull(navigationBarProps, "NavigationBarProps");
        }

        @NonNull
        @Override
        public MenuViewModel create() {
            return new MenuViewModel(mApplication, mNavigationBarProps);
        }
    }
}
