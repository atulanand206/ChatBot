package com.creations.mvvm.ui.checkbox;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.props.CheckboxProps;
import com.creations.mvvm.viewmodel.MVVMModule;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module()
public interface CheckboxModule extends MVVMModule {

    @Module
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static CheckboxViewModel.Factory provideChecklistViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final CheckboxProps checkboxProps) {
            Preconditions.requiresNonNull(activity, "ChecklistFragmentActivity");
            Preconditions.requiresNonNull(checkboxProps, "CheckboxProps");

            return new CheckboxViewModel.Factory(activity.getApplication(), checkboxProps);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<CheckboxContract.ViewModel,
            CheckboxViewModel> {

        @Provides
        @NonNull
        static CheckboxViewModel provideChecklistViewModel(
                @NonNull final CheckboxViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ChecklistViewModelFactory");
            Preconditions.requiresNonNull(application, "ChecklistFragmentActivity");

            CheckboxViewModel viewModel = ViewModelProviders.of(application, factory).get(CheckboxViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ProvidedChecklistViewModel");
        }

        @Binds
        @NonNull
        abstract CheckboxContract.ViewModel bindViewModel(@NonNull final CheckboxViewModel viewModel);
    }

}