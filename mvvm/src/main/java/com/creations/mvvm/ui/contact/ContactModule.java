package com.creations.mvvm.ui.contact;

import com.creations.condition.Preconditions;
import com.creations.mvvm.ui.editable.EditableModule;
import com.creations.mvvm.ui.editable.EditableViewModel;
import com.creations.mvvm.viewmodel.MVVMModule;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module()
public interface ContactModule extends MVVMModule {

    @Module(includes = EditableModule.InjectViewModelFactory.class)
    abstract class InjectViewModelFactory {

        @Provides
        @NonNull
        public static ContactViewModel.Factory provideContactViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final EditableViewModel.Factory editableFactory) {
            Preconditions.requiresNonNull(activity, "ContactFragmentActivity");
            Preconditions.requiresNonNull(editableFactory, "EditableFactory");

            return new ContactViewModel.Factory(activity.getApplication(), editableFactory);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<ContactContract.ViewModel,
            ContactViewModel> {

        @Provides
        @NonNull
        static ContactViewModel provideContactViewModel(
                @NonNull final ContactViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ContactViewModelFactory");
            Preconditions.requiresNonNull(application, "ContactFragmentActivity");

            ContactViewModel viewModel = ViewModelProviders.of(application, factory).get(ContactViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ProvidedContactViewModel");
        }

        @Binds
        @NonNull
        abstract ContactContract.ViewModel bindViewModel(@NonNull final ContactViewModel viewModel);
    }

}