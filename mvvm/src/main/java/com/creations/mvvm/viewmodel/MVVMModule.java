package com.creations.mvvm.viewmodel;

import com.creations.condition.Preconditions;
import com.creations.mvvm.fragment.MVVMFragmentView;
import com.example.dagger.scopes.FragmentScope;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import dagger.android.AndroidInjector;

public interface MVVMModule {

    abstract class InjectViewModel<CVM extends IMVVMViewModel, VM extends CVM> {
        public static <V extends MVVMFragmentView, VM extends MVVMViewModel, F extends MVVMViewModel.Factory>
                VM provideViewModel(@NonNull final F factory, @NonNull final V fragment,
                                    @NonNull final Class<VM> klass) {
            Preconditions.requiresNonNull(factory, "ProfileViewModelFactory");
            Preconditions.requiresNonNull(fragment, "ProfileFragment");

            VM viewModel = ViewModelProviders.of(fragment, factory).get(klass);
            return Preconditions.requiresNonNull(viewModel, "ProvidedLoginViewModel");
        }

        @NonNull
        abstract CVM bindViewModel(@NonNull final VM viewModel);
    }

    @FragmentScope
    interface Subcomponent<V extends MVVMFragmentView> extends AndroidInjector<V> {
        abstract class Builder<V extends MVVMFragmentView> extends AndroidInjector.Builder<V> {


        }
    }

}
