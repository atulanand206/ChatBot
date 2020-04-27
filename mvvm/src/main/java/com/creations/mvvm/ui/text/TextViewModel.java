package com.creations.mvvm.ui.text;

import android.app.Application;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.prop.PropViewModel;

import androidx.annotation.NonNull;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class TextViewModel<T extends Props> extends PropViewModel<T> implements TextContract.ViewModel<T> {


    public TextViewModel(@NonNull final Application application,
                         @NonNull final T props) {
        super(application, props);

    }

    public static class Factory<T extends Props> extends PropViewModel.Factory<T> {

        @NonNull
        private final T mProps;

        public Factory(@NonNull final Application application,
                       @NonNull final T props) {
            super(application, props);
            mProps = Preconditions.requiresNonNull(props, "Props");
        }

        @NonNull
        @Override
        public TextViewModel create() {
            return new TextViewModel<>(mApplication, mProps);
        }
    }
}
