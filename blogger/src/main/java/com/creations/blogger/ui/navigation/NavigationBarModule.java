package com.creations.blogger.ui.navigation;

import com.creations.blogger.model.navigation.NavigationBarProps;
import com.creations.blogger.ui.navigation.item.NavItemModule;
import com.creations.blogger.ui.navigation.item.NavItemViewModel;
import com.creations.condition.Preconditions;
import com.creations.mvvm.viewmodel.MVVMModule;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module()
public interface NavigationBarModule extends MVVMModule {

    @Module(includes = {
            NavItemModule.InjectViewModelFactory.class
    })
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static NavigationBarViewModel.Factory provideNavigationBarViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final NavItemViewModel.Factory itemFactory,
                @NonNull final NavigationBarProps navigationBarProps) {
            Preconditions.requiresNonNull(activity, "NavigationBarFragmentActivity");
            Preconditions.requiresNonNull(navigationBarProps, "NavigationBarProps");

            return new NavigationBarViewModel.Factory(activity.getApplication(), itemFactory, navigationBarProps);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<NavigationBarContract.ViewModel,
            NavigationBarViewModel> {

        @Provides
        @NonNull
        static NavigationBarViewModel provideNavigationBarViewModel(
                @NonNull final NavigationBarViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "NavigationBarViewModelFactory");
            Preconditions.requiresNonNull(application, "NavigationBarFragmentActivity");

            NavigationBarViewModel viewModel = ViewModelProviders.of(application, factory).get(NavigationBarViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ProvidedNavigationBarViewModel");
        }

        @Binds
        @NonNull
        abstract NavigationBarContract.ViewModel bindViewModel(@NonNull final NavigationBarViewModel viewModel);
    }

}