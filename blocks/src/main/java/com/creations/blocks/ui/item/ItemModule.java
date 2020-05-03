package com.creations.blocks.ui.item;

import com.creations.blocks.models.Board;
import com.creations.blocks.ui.options.OptionsModule;
import com.creations.blocks.ui.options.OptionsViewModel;
import com.creations.condition.Preconditions;
import com.creations.mvvm.ui.menu.MenuModule;
import com.creations.mvvm.viewmodel.MVVMModule;
import com.example.application.messages.IMessageManager;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {
        OptionsModule.class
})
public interface ItemModule extends MenuModule {

    @Module(includes = {
            OptionsModule.InjectViewModelFactory.class
    })
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static ItemViewModel.Factory provideViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final OptionsViewModel.Factory factory,
                @NonNull final Board props,
                @NonNull final IMessageManager messageManager) {
            Preconditions.requiresNonNull(activity, "FragmentActivity");
            Preconditions.requiresNonNull(props, "Props");

            return new ItemViewModel.Factory(activity.getApplication(), factory, props);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<ItemContract.ViewModel,
            ItemViewModel> {

        @Provides
        @NonNull
        static ItemViewModel provideViewModel(
                @NonNull final ItemViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ViewModelFactory");
            Preconditions.requiresNonNull(application, "FragmentActivity");

            ItemViewModel viewModel = ViewModelProviders.of(application, factory).get(ItemViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ViewModel");
        }

        @Binds
        @NonNull
        abstract ItemContract.ViewModel bindViewModel(@NonNull final ItemViewModel viewModel);
    }

}