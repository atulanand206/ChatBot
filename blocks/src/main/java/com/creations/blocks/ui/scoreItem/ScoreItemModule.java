package com.creations.blocks.ui.scoreItem;

import com.creations.blocks.models.ScoreItem;
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

@Module()
public interface ScoreItemModule extends MenuModule {

    @Module
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static ScoreItemViewModel.Factory provideViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final ScoreItem props,
                @NonNull final IMessageManager messageManager) {
            Preconditions.requiresNonNull(activity, "FragmentActivity");
            Preconditions.requiresNonNull(props, "Props");

            return new ScoreItemViewModel.Factory(activity.getApplication(), props);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<ScoreItemContract.ViewModel,
            ScoreItemViewModel> {

        @Provides
        @NonNull
        static ScoreItemViewModel provideViewModel(
                @NonNull final ScoreItemViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ViewModelFactory");
            Preconditions.requiresNonNull(application, "FragmentActivity");

            ScoreItemViewModel viewModel = ViewModelProviders.of(application, factory).get(ScoreItemViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ViewModel");
        }

        @Binds
        @NonNull
        abstract ScoreItemContract.ViewModel bindViewModel(@NonNull final ScoreItemViewModel viewModel);
    }

}