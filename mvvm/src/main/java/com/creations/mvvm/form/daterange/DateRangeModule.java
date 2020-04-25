package com.creations.mvvm.form.daterange;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.props.DateRangeProps;
import com.creations.mvvm.viewmodel.MVVMModule;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module()
public interface DateRangeModule extends MVVMModule {

    @Module
    abstract class InjectViewModelFactory {
        @Provides
        @NonNull
        public static DateRangeViewModel.Factory provideDateRangeViewModelFactory(
                @NonNull final FragmentActivity activity,
                @NonNull final DateRangeProps dateRangeProps) {
            Preconditions.requiresNonNull(activity, "DateRangeFragmentActivity");
            Preconditions.requiresNonNull(dateRangeProps, "DateRangeProps");

            return new DateRangeViewModel.Factory(activity.getApplication(), dateRangeProps);
        }
    }

    @Module(includes = InjectViewModelFactory.class)
    abstract class InjectViewModel extends MVVMModule.InjectViewModel<DateRangeContract.ViewModel,
            DateRangeViewModel> {

        @Provides
        @NonNull
        static DateRangeViewModel provideDateRangeViewModel(
                @NonNull final DateRangeViewModel.Factory factory,
                @NonNull final FragmentActivity application) {
            Preconditions.requiresNonNull(factory, "DateRangeViewModelFactory");
            Preconditions.requiresNonNull(application, "DateRangeFragmentActivity");

            DateRangeViewModel viewModel = ViewModelProviders.of(application, factory).get(DateRangeViewModel.class);
            return Preconditions.requiresNonNull(viewModel, "ProvidedDateRangeViewModel");
        }

        @Binds
        @NonNull
        abstract DateRangeContract.ViewModel bindViewModel(@NonNull final DateRangeViewModel viewModel);
    }

}