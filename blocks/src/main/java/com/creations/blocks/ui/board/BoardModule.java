package com.creations.blocks.ui.board;

import com.creations.blocks.api.IAPIBlocks;
import com.creations.blocks.models.Board;
import com.creations.blocks.ui.row.RowModule;
import com.creations.blocks.ui.row.RowViewModel;
import com.creations.blocks.ui.word.WordModule;
import com.creations.blocks.ui.word.WordViewModel;
import com.creations.condition.Preconditions;
import com.creations.mvvm.viewmodel.MVVMModule;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = {
        WordModule.class,
        RowModule.class
})
public interface BoardModule extends MVVMModule {

    @Module(includes = {
            RowModule.InjectViewModelFactory.class,
            WordModule.InjectViewModelFactory.class
    })
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static BoardViewModel.Factory provideViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final RowViewModel.Factory cellFactory,
                @NonNull final WordViewModel.Factory worddFactory,
                @NonNull final IAPIBlocks apiChat,
                @NonNull final Board props) {
            Preconditions.requiresNonNull(activity, "FragmentActivity");
            Preconditions.requiresNonNull(props, "Props");

            return new BoardViewModel.Factory(activity.getApplication(), cellFactory, worddFactory, apiChat, props);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<BoardContract.ViewModel,
            BoardViewModel> {

        @Provides
        @NonNull
        static BoardViewModel provideViewModel(
                @NonNull final BoardViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ViewModelFactory");
            Preconditions.requiresNonNull(application, "FragmentActivity");

            BoardViewModel viewModel = ViewModelProviders.of(application, factory).get(BoardViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ViewModel");
        }

        @Binds
        @NonNull
        abstract BoardContract.ViewModel bindViewModel(@NonNull final BoardViewModel viewModel);
    }

}