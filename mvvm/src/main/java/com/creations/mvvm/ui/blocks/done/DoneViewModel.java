package com.creations.mvvm.ui.blocks.done;

import android.app.Application;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.blocks.Done;
import com.creations.mvvm.ui.menu.MenuViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class DoneViewModel extends MenuViewModel<Done> implements DoneContract.ViewModel<Done> {


    public DoneViewModel(@NonNull final Application application,
                         @NonNull final Done props) {
        super(application, props);
    }

    @Override
    public void done(String word) {
        getToastEvent().postEvent(word);
    }

    public static class Factory extends MVVMViewModel.Factory<DoneViewModel> {

        @NonNull
        private final Done mProps;

        public Factory(@NonNull final Application application,
                       @NonNull final Done props) {
            super(DoneViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
        }

        @NonNull
        @Override
        public DoneViewModel create() {
            return new DoneViewModel(mApplication, mProps);
        }
    }
}
