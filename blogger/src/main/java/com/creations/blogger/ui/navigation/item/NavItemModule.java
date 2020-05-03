package com.creations.blogger.ui.navigation.item;

import com.creations.blogger.model.navigation.NavigationItem;
import com.creations.condition.Preconditions;
import com.creations.mvvm.viewmodel.MVVMModule;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module()
public interface NavItemModule extends MVVMModule {

    @Module
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static NavItemViewModel.Factory provideNavigationBarViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final NavigationItem navigationBarProps) {
            Preconditions.requiresNonNull(activity, "NavigationBarFragmentActivity");
            Preconditions.requiresNonNull(navigationBarProps, "NavigationBarProps");

            return new NavItemViewModel.Factory(activity.getApplication(), navigationBarProps);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<NavItemContract.ViewModel,
            NavItemViewModel> {

        @Provides
        @NonNull
        static NavItemViewModel provideNavigationBarViewModel(
                @NonNull final NavItemViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "NavigationBarViewModelFactory");
            Preconditions.requiresNonNull(application, "NavigationBarFragmentActivity");

            NavItemViewModel viewModel = ViewModelProviders.of(application, factory).get(NavItemViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ProvidedNavigationBarViewModel");
        }

        @Binds
        @NonNull
        abstract NavItemContract.ViewModel bindViewModel(@NonNull final NavItemViewModel viewModel);
    }

}