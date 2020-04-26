package com.creations.mvvm.ui.button;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.props.ButtonProps;
import com.creations.mvvm.viewmodel.MVVMModule;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module()
public interface ButtonModule extends MVVMModule {

    @Module
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static ButtonViewModel.Factory provideButtonViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final ButtonProps ButtonProps) {
            Preconditions.requiresNonNull(activity, "ButtonFragmentActivity");
            Preconditions.requiresNonNull(ButtonProps, "ButtonProps");

            return new ButtonViewModel.Factory(activity.getApplication(), ButtonProps);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<ButtonContract.ViewModel,
            ButtonViewModel> {

        @Provides
        @NonNull
        static ButtonViewModel provideButtonViewModel(
                @NonNull final ButtonViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ButtonViewModelFactory");
            Preconditions.requiresNonNull(application, "ButtonFragmentActivity");

            ButtonViewModel viewModel = ViewModelProviders.of(application, factory).get(ButtonViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ProvidedButtonViewModel");
        }

        @Binds
        @NonNull
        abstract ButtonContract.ViewModel bindViewModel(@NonNull final ButtonViewModel viewModel);
    }

}