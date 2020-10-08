package com.creations.naina.ui.contact;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.props.EditableProps;
import com.creations.mvvm.ui.editable.EditableModule;
import com.creations.mvvm.ui.editable.EditableViewModel;
import com.creations.mvvm.ui.text.TextModule;
import com.creations.mvvm.ui.text.TextViewModel;
import com.creations.mvvm.viewmodel.MVVMModule;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module()
public interface ContactModule extends MVVMModule {

    @Module(includes = {
            EditableModule.InjectViewModelFactory.class,
            TextModule.InjectViewModelFactory.class
    })
    abstract class InjectViewModelFactory {

        @Provides
        public static EditableProps editableProps() {
            return new EditableProps();
        }

        @Provides
        @NonNull
        public static ContactViewModel.Factory provideContactViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final TextViewModel.Factory textFactory,
                @NonNull final EditableViewModel.Factory editableFactory) {
            Preconditions.requiresNonNull(activity, "ContactFragmentActivity");
            Preconditions.requiresNonNull(editableFactory, "EditableFactory");

            return new ContactViewModel.Factory(activity.getApplication(), textFactory, editableFactory);
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