package com.creations.mvvm.ui.blank;

import android.app.Application;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class FreshViewModel extends MenuViewModel<Props> implements FreshContract.ViewModel<Props> {


    public FreshViewModel(@NonNull final Application application,
                          @NonNull final Props props) {
        super(application, props);

    }

    public static class Factory extends MVVMViewModel.Factory<FreshViewModel> {

        @NonNull
        private final Props mProps;

        public Factory(@NonNull final Application application,
                       @NonNull final Props props) {
            super(FreshViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
        }

        @NonNull
        @Override
        public FreshViewModel create() {
            return new FreshViewModel(mApplication, mProps);
        }
    }
}
