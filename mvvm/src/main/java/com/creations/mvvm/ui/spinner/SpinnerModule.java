package com.creations.mvvm.ui.spinner;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.props.SpinnerProps;
import com.creations.mvvm.viewmodel.MVVMModule;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module()
public interface SpinnerModule extends MVVMModule {

    @Module
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static SpinnerViewModel.Factory provideSpinnerViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final SpinnerProps SpinnerProps) {
            Preconditions.requiresNonNull(activity, "SpinnerFragmentActivity");
            Preconditions.requiresNonNull(SpinnerProps, "SpinnerProps");

            return new SpinnerViewModel.Factory(activity.getApplication(), SpinnerProps);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<SpinnerContract.ViewModel,
            SpinnerViewModel> {

        @Provides
        @NonNull
        public static SpinnerViewModel provideSpinnerViewModel(
                @NonNull final SpinnerViewModel.Factory factory,
                @NonNull final FragmentActivity activity) {
            Preconditions.requiresNonNull(factory, "SpinnerViewModelFactory");
            Preconditions.requiresNonNull(activity, "SpinnerFragmentActivity");

            SpinnerViewModel viewModel = ViewModelProviders.of(activity, factory).get(SpinnerViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ProvidedSpinnerViewModel");
        }

        @Binds
        @NonNull
        abstract SpinnerContract.ViewModel bindViewModel(@NonNull final SpinnerViewModel viewModel);
    }

}