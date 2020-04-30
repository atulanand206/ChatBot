package com.creations.mvvm.ui.blocks.board;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.blocks.Board;
import com.creations.mvvm.ui.blocks.row.RowModule;
import com.creations.mvvm.ui.blocks.row.RowViewModel;
import com.creations.mvvm.viewmodel.MVVMModule;
import com.creations.tools.utils.JsonConvertor;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = RowModule.class)
public interface BoardModule extends MVVMModule {

    @Module(includes = {
            RowModule.InjectViewModelFactory.class,
    })
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static BoardViewModel.Factory provideViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final RowViewModel.Factory cellFactory,
                @NonNull final JsonConvertor jsonConvertor,
                @NonNull final Board props) {
            Preconditions.requiresNonNull(activity, "FragmentActivity");
            Preconditions.requiresNonNull(props, "Props");

            return new BoardViewModel.Factory(activity.getApplication(), cellFactory, jsonConvertor, props);
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