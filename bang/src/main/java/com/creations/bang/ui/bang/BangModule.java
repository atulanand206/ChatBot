package com.creations.bang.ui.bang;

import com.creations.bang.di.PropsModule;
import com.creations.bang.models.Bang;
import com.creations.bang.ui.card.CardModule;
import com.creations.bang.ui.card.CardViewModel;
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

@Module(includes = {CardModule.class, PropsModule.class})
public interface BangModule extends MenuModule {

    @Module(includes = CardModule.InjectViewModelFactory.class)
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static BangViewModel.Factory provideViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final CardViewModel.Factory factory,
                @NonNull final Bang props,
                @NonNull final IMessageManager messageManager) {
            Preconditions.requiresNonNull(activity, "FragmentActivity");
            Preconditions.requiresNonNull(props, "Props");

            return new BangViewModel.Factory(activity.getApplication(), factory, props);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<BangContract.ViewModel,
            BangViewModel> {

        @Provides
        @NonNull
        static BangViewModel provideViewModel(
                @NonNull final BangViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ViewModelFactory");
            Preconditions.requiresNonNull(application, "FragmentActivity");

            BangViewModel viewModel = ViewModelProviders.of(application, factory).get(BangViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ViewModel");
        }

        @Binds
        @NonNull
        abstract BangContract.ViewModel bindViewModel(@NonNull final BangViewModel viewModel);
    }

}