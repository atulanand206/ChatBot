package com.creations.inception.ui.form;

import com.creations.blocks.di.PropsModule;
import com.creations.blocks.models.Preset;
import com.creations.blocks.ui.container.ContainerModule;
import com.creations.blocks.ui.container.ContainerViewModel;
import com.creations.blogger.ui.blogger.BloggerModule;
import com.creations.blogger.ui.blogger.BloggerViewModel;
import com.creations.blogger.ui.navigation.NavigationBarModule;
import com.creations.blogger.ui.navigation.NavigationBarViewModel;
import com.creations.condition.Preconditions;
import com.creations.mvvm.ui.button.ButtonModule;
import com.creations.mvvm.ui.contact.ContactModule;
import com.creations.mvvm.ui.daterange.DateRangeModule;
import com.creations.mvvm.ui.drawer.DrawerModule;
import com.creations.mvvm.ui.drawer.DrawerViewModel;
import com.creations.mvvm.ui.editable.EditableModule;
import com.creations.mvvm.ui.image.ImageModule;
import com.creations.mvvm.ui.spinner.SpinnerModule;
import com.creations.mvvm.viewmodel.MVVMModule;
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

@Module(subcomponents = RequestModule.RequestSubcomponent.class)
public interface RequestModule extends MVVMModule {

    @Module(includes = {
            EditableModule.InjectViewModelFactory.class,
            ContactModule.InjectViewModelFactory.class,
            DateRangeModule.InjectViewModelFactory.class,
            SpinnerModule.InjectViewModelFactory.class,
            ButtonModule.InjectViewModelFactory.class,
            NavigationBarModule.InjectViewModelFactory.class,
            ImageModule.InjectViewModelFactory.class,
            BloggerModule.InjectViewModelFactory.class,
            DrawerModule.InjectViewModelFactory.class,
            ContainerModule.InjectViewModelFactory.class,
            PropsModule.class
    })
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static FragmentActivity provideFragmentActivity(@NonNull final RequestFragment fragment) {
            Preconditions.requiresNonNull(fragment, "SGIRequestFragment");

            return Preconditions.verifyNonNull(fragment.getActivity(), "GetActivity");
        }

        @Provides
        @NonNull
        public static RequestViewModel.RequestFactory provideNotamRequestViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final NavigationBarViewModel.Factory navigationFactory,
                @NonNull final BloggerViewModel.Factory bloggerFactory,
                @NonNull final DrawerViewModel.Factory drawerFactory,
                @NonNull final ContainerViewModel.Factory boardFactory,
                @NonNull final Preset preset) {

            return new RequestViewModel.RequestFactory(activity.getApplication(), navigationFactory,
                    bloggerFactory, drawerFactory, boardFactory, preset);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<RequestContract.ViewModel,
            RequestViewModel> {

        @Provides
        @NonNull
        static RequestViewModel provideNotamRequestViewModel(
                @NonNull final RequestViewModel.RequestFactory requestFactory,
                @NonNull final RequestFragment fragment) {
            Preconditions.requiresNonNull(requestFactory, "ViewModelFactory");
            Preconditions.requiresNonNull(fragment, "Fragment");

            RequestViewModel viewModel = ViewModelProviders.of(fragment, requestFactory).get(RequestViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ViewModel");
        }

        @Binds
        @NonNull
        abstract RequestContract.ViewModel bindViewModel(@NonNull final RequestViewModel viewModel);

    }

    @Binds
    @IntoMap
    @CustomFragmentKey(RequestFragment.class)
    AndroidInjector.Builder<? extends Fragment> bindAndroidInjectorFactory(RequestSubcomponent.Builder builder);

    @dagger.Subcomponent(modules = InjectViewModel.class)
    @FragmentScope
    interface RequestSubcomponent extends Subcomponent<RequestFragment> {

        @dagger.Subcomponent.Builder
        abstract class Builder extends Subcomponent.Builder<RequestFragment> {

            @BindsInstance
            @NonNull
            public abstract Builder preset(@NonNull final Preset preset);
        }

    }
}
