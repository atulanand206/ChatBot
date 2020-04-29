package com.creations.mvvm.ui.text;

import android.app.Application;
import android.text.Editable;

import com.creations.condition.Preconditions;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.edit.EditViewModel;
import com.example.application.utils.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class TextViewModel<T extends Props> extends EditViewModel<T> implements TextContract.ViewModel<T> {

    @NonNull
    private final MutableLiveData<String> mText = new MutableLiveData<>();


    public TextViewModel(@NonNull final Application application,
                         @NonNull final T props) {
        super(application, props);

    }

    @NonNull
    @Override
    public LiveData<String> getText() {
        return mText;
    }

    @Override
    public void setText(@NonNull final String txt) {
        mText.postValue(txt);
    }

    @Override
    public void afterTextChanged(@Nullable final Editable editable) {
        String text = null;
        if (editable != null) {
            text = editable.toString();
        }
        if (TextUtils.isEmpty(text)) {
            text = null;
        }
        mText.setValue(text);
    }

    public static class Factory<T extends Props> extends EditViewModel.Factory<T> {

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
