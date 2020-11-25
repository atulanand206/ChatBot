package com.creations.script.ui.information;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuModule;
import com.creations.mvvm.viewmodel.MVVMModule;
import com.creations.script.models.Information;
import com.example.application.messages.IMessageManager;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module()
public interface InformationModule extends MenuModule {

    @Module
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static InformationViewModel.Factory provideViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final Information props,
                @NonNull final IMessageManager messageManager) {
            Preconditions.requiresNonNull(activity, "FragmentActivity");
            Preconditions.requiresNonNull(props, "Props");

            return new InformationViewModel.Factory(activity.getApplication(), props);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<InformationContract.ViewModel,
            InformationViewModel> {

        @Provides
        @NonNull
        static InformationViewModel provideViewModel(
                @NonNull final InformationViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ViewModelFactory");
            Preconditions.requiresNonNull(application, "FragmentActivity");

            InformationViewModel viewModel = ViewModelProviders.of(application, factory).get(InformationViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ViewModel");
        }

        @Binds
        @NonNull
        abstract InformationContract.ViewModel bindViewModel(@NonNull final InformationViewModel viewModel);
    }

}