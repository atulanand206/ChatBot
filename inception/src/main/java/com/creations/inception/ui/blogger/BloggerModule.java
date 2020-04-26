package com.creations.inception.ui.blogger;

import com.creations.blogger.IAPIChat;
import com.creations.condition.Preconditions;
import com.creations.mvvm.form.image.ImageViewModel;
import com.creations.mvvm.viewmodel.MVVMModule;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module()
public interface BloggerModule extends MVVMModule {

    @Module
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static BloggerViewModel.Factory provideButtonViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final ImageViewModel.Factory imageFactory,
                @NonNull final IAPIChat airspaceApi) {
            Preconditions.requiresNonNull(activity, "ButtonFragmentActivity");
            Preconditions.requiresNonNull(imageFactory, "ImageFactory");
            Preconditions.requiresNonNull(airspaceApi, "AirspaceApi");
            return new BloggerViewModel.Factory(activity.getApplication(), imageFactory, airspaceApi);
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