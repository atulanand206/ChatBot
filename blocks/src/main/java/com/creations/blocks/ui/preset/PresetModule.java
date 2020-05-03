package com.creations.blocks.ui.preset;

import com.creations.blocks.api.IAPIBlocks;
import com.creations.blocks.models.Preset;
import com.creations.blocks.ui.item.ItemModule;
import com.creations.blocks.ui.item.ItemViewModel;
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
        ItemModule.class
})
public interface PresetModule extends MenuModule {

    @Module(includes = {
            ItemModule.InjectViewModelFactory.class
    })
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static PresetViewModel.Factory provideViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final Preset props,
                @NonNull final IAPIBlocks apiChat,
                @NonNull final ItemViewModel.Factory itemFactory,
                @NonNull final IMessageManager messageManager) {
            Preconditions.requiresNonNull(activity, "FragmentActivity");
            Preconditions.requiresNonNull(props, "Props");

            return new PresetViewModel.Factory(activity.getApplication(), apiChat, itemFactory, props);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<PresetContract.ViewModel,
            PresetViewModel> {

        @Provides
        @NonNull
        static PresetViewModel provideViewModel(
                @NonNull final PresetViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ViewModelFactory");
            Preconditions.requiresNonNull(application, "FragmentActivity");

            PresetViewModel viewModel = ViewModelProviders.of(application, factory).get(PresetViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ViewModel");
        }

        @Binds
        @NonNull
        abstract PresetContract.ViewModel bindViewModel(@NonNull final PresetViewModel viewModel);
    }

}