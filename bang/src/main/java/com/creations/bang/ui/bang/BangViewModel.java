package com.creations.bang.ui.bang;

import android.app.Application;

import com.creations.bang.models.Bang;
import com.creations.condition.Preconditions;
import com.creations.mvvm.ui.menu.MenuViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;

import static android.view.View.VISIBLE;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class BangViewModel extends MenuViewModel<Bang> implements BangContract.ViewModel<Bang> {

    public BangViewModel(@NonNull final Application application,
                         @NonNull final Bang props) {
        super(application, props);
        setVisibility(VISIBLE);
    }

    public static class Factory extends MVVMViewModel.Factory<BangViewModel> {

        @NonNull
        private final Bang mProps;

        public Factory(@NonNull final Application application,
                       @NonNull final Bang props) {
            super(BangViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
        }

        @NonNull
        @Override
        public BangViewModel create() {
            return new BangViewModel(mApplication, mProps);
        }
    }
}
