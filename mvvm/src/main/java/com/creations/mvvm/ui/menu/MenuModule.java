package com.creations.mvvm.ui.menu;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.navigation.NavigationBarProps;
import com.creations.mvvm.viewmodel.MVVMModule;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module()
public interface MenuModule extends MVVMModule {

    @Module
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static MenuViewModel.Factory provideNavigationBarViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final NavigationBarProps navigationBarProps) {
            Preconditions.requiresNonNull(activity, "NavigationBarFragmentActivity");
            Preconditions.requiresNonNull(navigationBarProps, "NavigationBarProps");

            return new MenuViewModel.Factory(activity.getApplication(), navigationBarProps);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<MenuContract.ViewModel,
            MenuViewModel> {

        @Provides
        @NonNull
        static MenuViewModel provideNavigationBarViewModel(
                @NonNull final MenuViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "NavigationBarViewModelFactory");
            Preconditions.requiresNonNull(application, "NavigationBarFragmentActivity");

            MenuViewModel viewModel = ViewModelProviders.of(application, factory).get(MenuViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ProvidedNavigationBarViewModel");
        }

        @Binds
        @NonNull
        abstract MenuContract.ViewModel bindViewModel(@NonNull final MenuViewModel viewModel);
    }

}