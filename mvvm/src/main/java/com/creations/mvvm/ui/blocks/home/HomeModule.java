package com.creations.mvvm.ui.blocks.home;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.blocks.Home;
import com.creations.mvvm.ui.menu.MenuModule;
import com.creations.mvvm.viewmodel.MVVMModule;
import com.example.application.messages.IMessageManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module()
public interface HomeModule extends MenuModule {

    @Module
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static HomeViewModel.Factory provideViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final Home props,
                @NonNull final IMessageManager messageManager) {
            Preconditions.requiresNonNull(activity, "FragmentActivity");
            Preconditions.requiresNonNull(props, "Props");

            return new HomeViewModel.Factory(activity.getApplication(), props);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<HomeContract.ViewModel,
            HomeViewModel> {

        @Provides
        @NonNull
        static HomeViewModel provideViewModel(
                @NonNull final HomeViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ViewModelFactory");
            Preconditions.requiresNonNull(application, "FragmentActivity");

            HomeViewModel viewModel = ViewModelProviders.of(application, factory).get(HomeViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ViewModel");
        }

        @Binds
        @NonNull
        abstract HomeContract.ViewModel bindViewModel(@NonNull final HomeViewModel viewModel);
    }

}