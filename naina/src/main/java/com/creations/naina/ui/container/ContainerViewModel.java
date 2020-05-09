package com.creations.naina.ui.container;

import android.app.Application;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class ContainerViewModel extends MenuViewModel<Props> implements ContainerContract.ViewModel<Props> {


    public ContainerViewModel(@NonNull final Application application,
                              @NonNull final Props props) {
        super(application, props);

    }

    public static class Factory extends MVVMViewModel.Factory<ContainerViewModel> {

        @NonNull
        private final Props mProps;

        public Factory(@NonNull final Application application,
                       @NonNull final Props props) {
            super(ContainerViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
        }

        @NonNull
        @Override
        public ContainerViewModel create() {
            return new ContainerViewModel(mApplication, mProps);
        }
    }
}
