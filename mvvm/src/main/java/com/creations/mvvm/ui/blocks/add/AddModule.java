package com.creations.mvvm.ui.blocks.add;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.blocks.Add;
import com.creations.mvvm.ui.blocks.board.BoardModule;
import com.creations.mvvm.ui.blocks.board.BoardViewModel;
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
public interface AddModule extends MenuModule {

    @Module(includes = {
            BoardModule.InjectViewModelFactory.class,
    })
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static AddViewModel.Factory provideViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final Add props,
                @NonNull final BoardViewModel.Factory cellFactory,
                @NonNull final IMessageManager messageManager) {
            Preconditions.requiresNonNull(activity, "FragmentActivity");
            Preconditions.requiresNonNull(props, "Props");

            return new AddViewModel.Factory(activity.getApplication(), cellFactory, props);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<AddContract.ViewModel,
            AddViewModel> {

        @Provides
        @NonNull
        static AddViewModel provideViewModel(
                @NonNull final AddViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ViewModelFactory");
            Preconditions.requiresNonNull(application, "FragmentActivity");

            AddViewModel viewModel = ViewModelProviders.of(application, factory).get(AddViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ViewModel");
        }

        @Binds
        @NonNull
        abstract AddContract.ViewModel bindViewModel(@NonNull final AddViewModel viewModel);
    }

}