package com.creations.bang.ui.card;

import com.creations.bang.models.Card;
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
public interface CardModule extends MenuModule {

    @Module
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static CardViewModel.Factory provideViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final Card props,
                @NonNull final IMessageManager messageManager) {
            Preconditions.requiresNonNull(activity, "FragmentActivity");
            Preconditions.requiresNonNull(props, "Props");

            return new CardViewModel.Factory(activity.getApplication(), props);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<CardContract.ViewModel,
            CardViewModel> {

        @Provides
        @NonNull
        static CardViewModel provideViewModel(
                @NonNull final CardViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ViewModelFactory");
            Preconditions.requiresNonNull(application, "FragmentActivity");

            CardViewModel viewModel = ViewModelProviders.of(application, factory).get(CardViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ViewModel");
        }

        @Binds
        @NonNull
        abstract CardContract.ViewModel bindViewModel(@NonNull final CardViewModel viewModel);
    }

}