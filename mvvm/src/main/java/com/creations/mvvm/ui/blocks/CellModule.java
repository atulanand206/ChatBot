package com.creations.mvvm.ui.blocks;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.blocks.Cell;
import com.creations.mvvm.ui.PropsModule;
import com.creations.mvvm.viewmodel.MVVMModule;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module()
public interface CellModule extends MVVMModule {

    @Module(includes = {
            PropsModule.class
    })
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static CellViewModel.Factory provideViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final Cell prop) {
            Preconditions.requiresNonNull(activity, "FragmentActivity");
            Preconditions.requiresNonNull(prop, "Props");

            return new CellViewModel.Factory(activity.getApplication(), prop);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<CellContract.ViewModel,
            CellViewModel> {

        @Provides
        @NonNull
        static CellViewModel provideViewModel(
                @NonNull final CellViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ViewModelFactory");
            Preconditions.requiresNonNull(application, "FragmentActivity");

            CellViewModel viewModel = ViewModelProviders.of(application, factory).get(CellViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ViewModel");
        }

        @Binds
        @NonNull
        abstract CellContract.ViewModel bindViewModel(@NonNull final CellViewModel viewModel);
    }

}