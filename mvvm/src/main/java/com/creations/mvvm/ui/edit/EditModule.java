package com.creations.mvvm.ui.edit;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.props.Props;
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
public interface EditModule extends MenuModule {

    @Module
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static EditViewModel.Factory provideViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final Props props,
                @NonNull final IMessageManager messageManager) {
            Preconditions.requiresNonNull(activity, "FragmentActivity");
            Preconditions.requiresNonNull(props, "Props");

            return new EditViewModel.Factory<>(activity.getApplication(), props);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<EditContract.ViewModel,
            EditViewModel> {

        @Provides
        @NonNull
        static EditViewModel provideViewModel(
                @NonNull final EditViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ViewModelFactory");
            Preconditions.requiresNonNull(application, "FragmentActivity");

            EditViewModel viewModel = ViewModelProviders.of(application, factory).get(EditViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ViewModel");
        }

        @Binds
        @NonNull
        abstract EditContract.ViewModel bindViewModel(@NonNull final EditViewModel viewModel);
    }

}