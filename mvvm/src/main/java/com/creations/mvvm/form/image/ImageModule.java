package com.creations.mvvm.form.image;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.props.ImageData;
import com.creations.mvvm.viewmodel.MVVMModule;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module()
public interface ImageModule extends MVVMModule {

    @Module
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static ImageViewModel.Factory provideButtonViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final ImageData imageData) {
            Preconditions.requiresNonNull(activity, "ButtonFragmentActivity");
            Preconditions.requiresNonNull(imageData, "imageData");

            return new ImageViewModel.Factory(activity.getApplication(), imageData);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<ImageContract.ViewModel,
            ImageViewModel> {

        @Provides
        @NonNull
        static ImageViewModel provideButtonViewModel(
                @NonNull final ImageViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ButtonViewModelFactory");
            Preconditions.requiresNonNull(application, "ButtonFragmentActivity");

            ImageViewModel viewModel = ViewModelProviders.of(application, factory).get(ImageViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ProvidedButtonViewModel");
        }

        @Binds
        @NonNull
        abstract ImageContract.ViewModel bindViewModel(@NonNull final ImageViewModel viewModel);
    }

}