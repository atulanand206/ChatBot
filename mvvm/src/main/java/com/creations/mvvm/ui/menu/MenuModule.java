package com.creations.mvvm.ui.menu;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.viewmodel.MVVMModule;
import com.creations.tools.utils.JsonConvertor;
import com.example.application.messages.IMessageManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module()
public interface MenuModule extends MVVMModule {

    @Module
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static MenuViewModel.Factory provideViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final Props props,
                @NonNull final IMessageManager messageManager,
                @NonNull final JsonConvertor jsonConvertor) {
            Preconditions.requiresNonNull(activity, "FragmentActivity");
            Preconditions.requiresNonNull(props, "Props");
            Preconditions.requiresNonNull(messageManager, "MessageManager");
            Preconditions.requiresNonNull(jsonConvertor, "JsonConvertor");

            return new MenuViewModel.Factory(activity.getApplication(), props, messageManager, jsonConvertor);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<MenuContract.ViewModel,
            MenuViewModel> {

        @Provides
        @NonNull
        static MenuViewModel provideViewModel(
                @NonNull final MenuViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ViewModelFactory");
            Preconditions.requiresNonNull(application, "FragmentActivity");

            MenuViewModel viewModel = ViewModelProviders.of(application, factory).get(MenuViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ViewModel");
        }

        @Binds
        @NonNull
        abstract MenuContract.ViewModel bindViewModel(@NonNull final MenuViewModel viewModel);
    }

}