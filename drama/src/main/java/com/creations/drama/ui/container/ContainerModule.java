package com.creations.drama.ui.container;

import com.creations.condition.Preconditions;
import com.creations.drama.models.CanvasP;
import com.creations.mvvm.ui.menu.MenuModule;
import com.creations.mvvm.viewmodel.MVVMModule;
import com.example.application.messages.IMessageManager;
import com.example.dagger.key.CustomFragmentKey;
import com.example.dagger.scopes.FragmentScope;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.BindsInstance;
import dagger.Module;
import dagger.Provides;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

@Module(subcomponents = ContainerModule.ContainerSubcomponent.class)
public interface ContainerModule extends MenuModule {

    @Module
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static ContainerViewModel.Factory provideViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final CanvasP props,
                @NonNull final IMessageManager messageManager) {
            Preconditions.requiresNonNull(activity, "FragmentActivity");
            Preconditions.requiresNonNull(props, "Props");

            return new ContainerViewModel.Factory(activity.getApplication(), props);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<ContainerContract.ViewModel,
            ContainerViewModel> {

        @Provides
        @NonNull
        static ContainerViewModel provideViewModel(
                @NonNull final ContainerViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "ViewModelFactory");
            Preconditions.requiresNonNull(application, "FragmentActivity");

            ContainerViewModel viewModel = ViewModelProviders.of(application, factory).get(ContainerViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ViewModel");
        }

        @Binds
        @NonNull
        abstract ContainerContract.ViewModel bindViewModel(@NonNull final ContainerViewModel viewModel);
    }

    @Binds
    @IntoMap
    @CustomFragmentKey(ContainerFragment.class)
    AndroidInjector.Builder<? extends Fragment> bindAndroidInjectorFactory(ContainerSubcomponent.Builder builder);

    @dagger.Subcomponent(modules = InjectViewModel.class)
    @FragmentScope
    interface ContainerSubcomponent extends Subcomponent<ContainerFragment> {

        @dagger.Subcomponent.Builder
        abstract class Builder extends Subcomponent.Builder<ContainerFragment> {

            @BindsInstance
            @NonNull
            public abstract Builder canvas(@NonNull final CanvasP preset);
        }

    }
}