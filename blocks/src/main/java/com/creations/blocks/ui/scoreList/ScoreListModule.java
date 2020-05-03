package com.creations.blocks.ui.scoreList;

import com.creations.blocks.api.IAPIBlocks;
import com.creations.blocks.models.Scores;
import com.creations.blocks.ui.scoreItem.ScoreItemModule;
import com.creations.blocks.ui.scoreItem.ScoreItemViewModel;
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

@Module(includes = ScoreItemModule.class)
public interface ScoreListModule extends MenuModule {

    @Module(includes = ScoreItemModule.InjectViewModelFactory.class)
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static ScoreListViewModel.Factory provideViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final IAPIBlocks apiChat,
                @NonNull final ScoreItemViewModel.Factory factory,
                @NonNull final Scores props,
                @NonNull final IMessageManager messageManager) {
            Preconditions.requiresNonNull(activity, "FragmentActivity");
            Preconditions.requiresNonNull(props, "Props");

            return new ScoreListViewModel.Factory(activity.getApplication(), factory, apiChat, props);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<ScoreListContract.ViewModel,
            ScoreListViewModel> {

        @Provides
        @NonNull
        static ScoreListViewModel provideViewModel(
                @NonNull final ScoreListViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ViewModelFactory");
            Preconditions.requiresNonNull(application, "FragmentActivity");

            ScoreListViewModel viewModel = ViewModelProviders.of(application, factory).get(ScoreListViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ViewModel");
        }

        @Binds
        @NonNull
        abstract ScoreListContract.ViewModel bindViewModel(@NonNull final ScoreListViewModel viewModel);
    }

}