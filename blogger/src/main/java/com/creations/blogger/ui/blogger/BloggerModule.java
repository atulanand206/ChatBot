package com.creations.blogger.ui.blogger;

import com.creations.blogger.ui.navigation.NavigationBarModule;
import com.creations.blogger.ui.navigation.NavigationBarViewModel;
import com.creations.condition.Preconditions;
import com.creations.mvvm.ui.image.ImageViewModel;
import com.creations.mvvm.viewmodel.MVVMModule;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public interface BloggerModule extends MVVMModule {

    @Module(includes = {
            NavigationBarModule.InjectViewModelFactory.class,
    })
    abstract class InjectViewModelFactory {

        @Provides
        @NonNull
        public static BloggerViewModel.Factory provideButtonViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final ImageViewModel.Factory imageFactory,
                @NonNull final NavigationBarViewModel.Factory factory) {
            Preconditions.requiresNonNull(activity, "ButtonFragmentActivity");
            Preconditions.requiresNonNull(imageFactory, "ImageFactory");
            return new BloggerViewModel.Factory(activity.getApplication(), imageFactory, factory);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<BloggerContract.ViewModel,
            BloggerViewModel> {

        @Provides
        @NonNull
        static BloggerViewModel provideButtonViewModel(
                @NonNull final BloggerViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ButtonViewModelFactory");
            Preconditions.requiresNonNull(application, "ButtonFragmentActivity");

            BloggerViewModel viewModel = ViewModelProviders.of(application, factory).get(BloggerViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ProvidedButtonViewModel");
        }

        @Binds
        @NonNull
        abstract BloggerContract.ViewModel bindViewModel(@NonNull final BloggerViewModel viewModel);
    }

}