package com.creations.mvvm.ui.blocks.score;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.blocks.Score;
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
public interface ScoreModule extends MenuModule {

    @Module
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static ScoreViewModel.Factory provideViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final Score props,
                @NonNull final IMessageManager messageManager) {
            Preconditions.requiresNonNull(activity, "FragmentActivity");
            Preconditions.requiresNonNull(props, "Props");

            return new ScoreViewModel.Factory(activity.getApplication(), props);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<ScoreContract.ViewModel,
            ScoreViewModel> {

        @Provides
        @NonNull
        static ScoreViewModel provideViewModel(
                @NonNull final ScoreViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ViewModelFactory");
            Preconditions.requiresNonNull(application, "FragmentActivity");

            ScoreViewModel viewModel = ViewModelProviders.of(application, factory).get(ScoreViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ViewModel");
        }

        @Binds
        @NonNull
        abstract ScoreContract.ViewModel bindViewModel(@NonNull final ScoreViewModel viewModel);
    }

}