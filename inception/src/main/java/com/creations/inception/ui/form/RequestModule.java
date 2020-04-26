package com.creations.inception.ui.form;

import com.creations.condition.Preconditions;
import com.creations.inception.ui.blogger.BloggerModule;
import com.creations.inception.ui.blogger.BloggerViewModel;
import com.creations.mvvm.models.navigation.NavigationBarProps;
import com.creations.mvvm.models.navigation.NavigationState;
import com.creations.mvvm.models.props.ButtonProps;
import com.creations.mvvm.models.props.DateRangeProps;
import com.creations.mvvm.models.props.DrawerProps;
import com.creations.mvvm.models.props.EditableProps;
import com.creations.mvvm.models.props.ImageData;
import com.creations.mvvm.models.props.SpinnerProps;
import com.creations.mvvm.ui.button.ButtonModule;
import com.creations.mvvm.ui.contact.ContactModule;
import com.creations.mvvm.ui.daterange.DateRangeModule;
import com.creations.mvvm.ui.drawer.DrawerModule;
import com.creations.mvvm.ui.drawer.DrawerViewModel;
import com.creations.mvvm.ui.editable.EditableModule;
import com.creations.mvvm.ui.image.ImageModule;
import com.creations.mvvm.ui.navigation.NavigationBarModule;
import com.creations.mvvm.ui.navigation.NavigationBarViewModel;
import com.creations.mvvm.ui.spinner.SpinnerModule;
import com.creations.mvvm.viewmodel.MVVMModule;
import com.example.dagger.key.CustomFragmentKey;
import com.example.dagger.scopes.FragmentScope;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
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
            DrawerModule.InjectViewModelFactory.class
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
        public static NavigationBarProps provideNavigationProps() {
            return new NavigationBarProps.Builder()
                    .withNOVICE(NavigationState.COMPLETED)
                    .withINTERMEDIATE(NavigationState.CURRENT)
                    .withADVANCED(NavigationState.NOT_YET_OPENED)
                    .build();
        }

        @Provides
        @NonNull
        public static DateRangeProps provideDateRangeProps() {
            return new DateRangeProps(false);
        }

        @Provides
        @NonNull
        public static EditableProps provideEditableProps() {
            return new EditableProps(false);
        }

        @Provides
        @NonNull
        public static SpinnerProps provideSpinnerProps() {
            return new SpinnerProps(false);
        }

        @Provides
        @NonNull
        public static ButtonProps provideButtonProps() {
            return new ButtonProps("");
        }

        @Provides
        @NonNull
        public static ImageData provideImageData() {
            return new ImageData();
        }

        @Provides
        @NonNull
        public static DrawerProps provideDrawableProps() {
            return new DrawerProps(new ArrayList<>());
        }

        @Provides
        @NonNull
        public static RequestViewModel.RequestFactory provideNotamRequestViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final NavigationBarViewModel.Factory navigationFactory,
                @NonNull final BloggerViewModel.Factory bloggerFactory,
                @NonNull final DrawerViewModel.Factory drawerFactory) {

            return new RequestViewModel.RequestFactory(activity.getApplication(), navigationFactory, bloggerFactory, drawerFactory);
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

        }

    }
}
