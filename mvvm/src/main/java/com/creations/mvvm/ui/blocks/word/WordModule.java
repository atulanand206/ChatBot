package com.creations.mvvm.ui.blocks.word;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.blocks.Word;
import com.creations.mvvm.ui.blocks.CellModule;
import com.creations.mvvm.ui.blocks.CellViewModel;
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
        CellModule.class
})
public interface WordModule extends MenuModule {

    @Module(includes = {
            CellModule.InjectViewModelFactory.class
    })
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static WordViewModel.Factory provideViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final CellViewModel.Factory cellFactory,
                @NonNull final Word props,
                @NonNull final IMessageManager messageManager) {
            Preconditions.requiresNonNull(activity, "FragmentActivity");
            Preconditions.requiresNonNull(props, "Props");

            return new WordViewModel.Factory(activity.getApplication(), cellFactory, props);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<WordContract.ViewModel,
            WordViewModel> {

        @Provides
        @NonNull
        static WordViewModel provideViewModel(
                @NonNull final WordViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ViewModelFactory");
            Preconditions.requiresNonNull(application, "FragmentActivity");

            WordViewModel viewModel = ViewModelProviders.of(application, factory).get(WordViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ViewModel");
        }

        @Binds
        @NonNull
        abstract WordContract.ViewModel bindViewModel(@NonNull final WordViewModel viewModel);
    }

}