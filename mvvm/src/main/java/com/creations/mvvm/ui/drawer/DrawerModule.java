package com.creations.mvvm.ui.drawer;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.props.DrawerProps;
import com.creations.mvvm.viewmodel.MVVMModule;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module()
public interface DrawerModule extends MVVMModule {

    @Module
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static DrawerViewModel.Factory provideNavigationBarViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final DrawerProps drawerProps) {
            Preconditions.requiresNonNull(activity, "NavigationBarFragmentActivity");
            Preconditions.requiresNonNull(drawerProps, "DrawerProps");

            return new DrawerViewModel.Factory(activity.getApplication(), drawerProps);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<DrawerContract.ViewModel,
            DrawerViewModel> {

        @Provides
        @NonNull
        static DrawerViewModel provideNavigationBarViewModel(
                @NonNull final DrawerViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "NavigationBarViewModelFactory");
            Preconditions.requiresNonNull(application, "NavigationBarFragmentActivity");

            DrawerViewModel viewModel = ViewModelProviders.of(application, factory).get(DrawerViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ProvidedNavigationBarViewModel");
        }

        @Binds
        @NonNull
        abstract DrawerContract.ViewModel bindViewModel(@NonNull final DrawerViewModel viewModel);
    }

}