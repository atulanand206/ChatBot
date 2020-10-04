package com.creations.mvvm.ui.text;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuModule;
import com.creations.mvvm.viewmodel.MVVMModule;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module()
public interface TextModule extends MenuModule {

    @Module
    abstract class InjectViewModelFactory {

        @Provides
        @NonNull
        public static Props provideProps() {
            return new Props();
        }

        @Provides
        @NonNull
        public static TextViewModel.Factory provideViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final Props props) {
            Preconditions.requiresNonNull(activity, "FragmentActivity");
            Preconditions.requiresNonNull(props, "Props");

            return new TextViewModel.Factory<>(activity.getApplication(), props);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<TextContract.ViewModel,
            TextViewModel> {

        @Provides
        @NonNull
        static TextViewModel provideViewModel(
                @NonNull final TextViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ViewModelFactory");
            Preconditions.requiresNonNull(application, "FragmentActivity");

            TextViewModel viewModel = ViewModelProviders.of(application, factory).get(TextViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ViewModel");
        }

        @Binds
        @NonNull
        abstract TextContract.ViewModel bindViewModel(@NonNull final TextViewModel viewModel);
    }

}