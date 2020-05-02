package com.creations.mvvm.ui.blocks.options;

import android.app.Application;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.blocks.Options;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class OptionsViewModel extends RecyclerViewModel<Options> implements OptionsContract.ViewModel<Options> {


    public OptionsViewModel(@NonNull final Application application,
                            @NonNull final Options props) {
        super(application, props);
        setText(props.getWord());
    }

    public static class Factory extends MVVMViewModel.Factory<OptionsViewModel> {

        @NonNull
        private final Options mProps;

        public Factory(@NonNull final Application application,
                       @NonNull final Options props) {
            super(OptionsViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
        }

        @NonNull
        @Override
        public OptionsViewModel create() {
            return new OptionsViewModel(mApplication, mProps);
        }
    }
}
