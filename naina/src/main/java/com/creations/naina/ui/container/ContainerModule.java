package com.creations.naina.ui.container;

import com.creations.bang.ui.bang.BangModule;
import com.creations.bang.ui.bang.BangViewModel;
import com.creations.condition.Preconditions;
import com.creations.mvvm.ui.menu.MenuModule;
import com.creations.mvvm.ui.text.TextModule;
import com.creations.mvvm.ui.text.TextViewModel;
import com.creations.mvvm.viewmodel.MVVMModule;
import com.creations.naina.api.ConfigurationRepository;
import com.creations.naina.api.IConfigurationRepository;
import com.creations.naina.models.CanvasP;
import com.creations.naina.models.TextP;
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

@Module(includes = {BangModule.class}, subcomponents = ContainerModule.ContainerSubcomponent.class)
public interface ContainerModule extends MenuModule {

  @Module(includes = {
          BangModule.InjectViewModelFactory.class,
          TextModule.InjectViewModelFactory.class
  })
  abstract class InjectViewModelFactory {
    @Provides
    @NonNull
    public static FragmentActivity provideFragmentActivity(@NonNull final ContainerFragment fragment) {
      Preconditions.requiresNonNull(fragment, "Fragment");

      return Preconditions.verifyNonNull(fragment.getActivity(), "GetActivity");
    }

    @Provides
    @NonNull
    public static ContainerViewModel.Factory provideViewModelFactory(
            @NonNull final FragmentActivity activity,
            @NonNull final BangViewModel.Factory bangFactory,
            @NonNull final TextViewModel.Factory factory,
            @NonNull final CanvasP props,
            @NonNull final IConfigurationRepository configurationRepository) {
      Preconditions.requiresNonNull(activity, "FragmentActivity");
      Preconditions.requiresNonNull(props, "Props");

      return new ContainerViewModel.Factory(activity.getApplication(), bangFactory, factory, props, configurationRepository);
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