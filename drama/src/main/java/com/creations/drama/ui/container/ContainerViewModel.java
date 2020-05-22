package com.creations.drama.ui.container;

import android.app.Application;

import com.creations.condition.Preconditions;
import com.creations.drama.models.CanvasP;
import com.creations.mvvm.ui.menu.MenuViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class ContainerViewModel extends MenuViewModel<CanvasP> implements ContainerContract.ViewModel<CanvasP> {


    public ContainerViewModel(@NonNull final Application application,
                              @NonNull final CanvasP props) {
        super(application, props);

    }

    public static class Factory extends MVVMViewModel.Factory<ContainerViewModel> {

        @NonNull
        private final CanvasP mProps;

        public Factory(@NonNull final Application application,
                       @NonNull final CanvasP props) {
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
