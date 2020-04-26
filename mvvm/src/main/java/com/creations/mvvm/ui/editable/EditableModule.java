package com.creations.mvvm.ui.editable;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.props.EditableProps;
import com.creations.mvvm.viewmodel.MVVMModule;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module()
public interface EditableModule extends MVVMModule {

    @Module
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static EditableViewModel.Factory provideEditableViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final EditableProps editableProps) {
            Preconditions.requiresNonNull(activity, "EditableFragmentActivity");
            Preconditions.requiresNonNull(editableProps, "EditableProps");

            return new EditableViewModel.Factory(activity.getApplication(), editableProps);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<EditableContract.ViewModel,
            EditableViewModel> {

        @Provides
        @NonNull
        static EditableViewModel provideEditableViewModel(
                @NonNull final EditableViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "EditableViewModelFactory");
            Preconditions.requiresNonNull(application, "EditableFragmentActivity");

            EditableViewModel viewModel = ViewModelProviders.of(application, factory).get(EditableViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ProvidedEditableViewModel");
        }

        @Binds
        @NonNull
        abstract EditableContract.ViewModel bindViewModel(@NonNull final EditableViewModel viewModel);
    }

}