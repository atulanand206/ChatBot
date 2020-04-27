package com.creations.mvvm.ui.blocks.row;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.blocks.Row;
import com.creations.mvvm.ui.blocks.CellModule;
import com.creations.mvvm.ui.blocks.CellViewModel;
import com.creations.mvvm.viewmodel.MVVMModule;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module(includes = CellModule.class)
public interface RowModule extends MVVMModule {

    @Module(includes = {
            CellModule.InjectViewModelFactory.class,
    })
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static RowViewModel.Factory provideViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final CellViewModel.Factory cellFactory,
                @NonNull final Row props) {
            Preconditions.requiresNonNull(activity, "FragmentActivity");
            Preconditions.requiresNonNull(props, "Props");

            return new RowViewModel.Factory(activity.getApplication(), cellFactory, props);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<RowContract.ViewModel,
            RowViewModel> {

        @Provides
        @NonNull
        static RowViewModel provideViewModel(
                @NonNull final RowViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ViewModelFactory");
            Preconditions.requiresNonNull(application, "FragmentActivity");

            RowViewModel viewModel = ViewModelProviders.of(application, factory).get(RowViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ViewModel");
        }

        @Binds
        @NonNull
        abstract RowContract.ViewModel bindViewModel(@NonNull final RowViewModel viewModel);
    }

}