package com.creations.inception.ui.form;

import com.creations.condition.Info;
import com.creations.condition.Preconditions;
import com.creations.inception.network.IAPIChat;
import com.creations.mvvm.form.button.ButtonModule;
import com.creations.mvvm.form.button.ButtonViewModel;
import com.creations.mvvm.form.contact.ContactModule;
import com.creations.mvvm.form.daterange.DateRangeModule;
import com.creations.mvvm.form.daterange.DateRangeViewModel;
import com.creations.mvvm.form.editable.EditableModule;
import com.creations.mvvm.form.editable.EditableViewModel;
import com.creations.mvvm.form.navigation.NavigationBarModule;
import com.creations.mvvm.form.navigation.NavigationBarViewModel;
import com.creations.mvvm.form.spinner.SpinnerModule;
import com.creations.mvvm.form.spinner.SpinnerViewModel;
import com.creations.mvvm.models.navigation.NavigationBarProps;
import com.creations.mvvm.models.props.ButtonProps;
import com.creations.mvvm.models.props.DateRangeProps;
import com.creations.mvvm.models.props.EditableProps;
import com.creations.mvvm.models.props.SpinnerProps;
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

@Module(subcomponents = RequestModule.RequestSubcomponent.class)
public interface RequestModule extends MVVMModule {

    @Module(includes = {
            EditableModule.InjectViewModelFactory.class,
            ContactModule.InjectViewModelFactory.class,
            DateRangeModule.InjectViewModelFactory.class,
            SpinnerModule.InjectViewModelFactory.class,
            ButtonModule.InjectViewModelFactory.class,
            NavigationBarModule.InjectViewModelFactory.class
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
        public static RequestViewModel.Factory provideNotamRequestViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final Info info,
                @NonNull final NavigationBarProps navigationBarProps,
                @NonNull final NavigationBarViewModel.Factory navigationFactory,
                @NonNull final SpinnerViewModel.Factory spinnerFactory,
                @NonNull final ButtonViewModel.Factory buttonFactory,
                @NonNull final IAPIChat apiAirspace,
                @NonNull final DateRangeViewModel.Factory dateRangeFactory,
                @NonNull final EditableViewModel.Factory editableFactory,
                @NonNull final IMessageManager messageManager) {
            Preconditions.requiresNonNull(activity, "FragmentActivity");
            Preconditions.requiresNonNull(info, "Airspace");
            Preconditions.requiresNonNull(spinnerFactory, "SpinnerFactory");
            Preconditions.requiresNonNull(apiAirspace, "ApiAirspace");
            Preconditions.requiresNonNull(dateRangeFactory, "DateRangeFactory");
            Preconditions.requiresNonNull(editableFactory, "EditableViewModelFactory");
            Preconditions.requiresNonNull(buttonFactory, "ButtonFactory");
            Preconditions.requiresNonNull(messageManager, "IMessageManager");

            return new RequestViewModel.Factory(activity.getApplication(), info,
                    navigationBarProps, navigationFactory, spinnerFactory, dateRangeFactory, editableFactory,
                    buttonFactory, messageManager, apiAirspace);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<RequestContract.ViewModel,
            RequestViewModel> {

        @Provides
        @NonNull
        static RequestViewModel provideNotamRequestViewModel(
                @NonNull final RequestViewModel.Factory factory,
                @NonNull final RequestFragment fragment) {
            Preconditions.requiresNonNull(factory, "NotamRequestViewModelFactory");
            Preconditions.requiresNonNull(fragment, "NotamRequestFragment");

            RequestViewModel viewModel = ViewModelProviders.of(fragment, factory).get(RequestViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ProvidedNotamRequestViewModel");
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
            public abstract Builder info(@NonNull final Info info);

            @BindsInstance
            @NonNull
            public abstract Builder navigationProps(final NavigationBarProps props);

        }

    }
}
