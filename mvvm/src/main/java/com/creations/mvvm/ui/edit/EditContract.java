package com.creations.mvvm.ui.edit;

import com.creations.mvvm.live.LiveEvent;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.prop.PropContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.lifecycle.LiveData;

public interface EditContract {

    interface ViewModel<T extends Props> extends PropContract.ViewModel<T> {

        @NonNull
        LiveData<Boolean> getEditable();

        void setEditable(final boolean editable);

        @NonNull
        MutableLiveData<Boolean> getSelected();

        void setSelected(boolean selected);

        void shuffle(final boolean shuffle);

        int getActiveColor();

        void onClick();

        void onClick(@NonNull final Object object);

        @NonNull
        LiveEvent.Mutable<String> getToastEvent();

        void removeSetError(@Nullable final MutableLiveData<String> textFieldError,
                            @Nullable final MutableLiveData<Boolean> errorEnabled);

        void removePostError(@Nullable MutableLiveData<String> textFieldError,
                             @Nullable MutableLiveData<Boolean> errorEnabled);

        void setError(@Nullable MutableLiveData<String> textFieldError,
                      @Nullable MutableLiveData<Boolean> errorEnabled,
                      @StringRes int errorResId);

        void postError(@Nullable MutableLiveData<String> textFieldError,
                       @Nullable MutableLiveData<Boolean> errorEnabled,
                       @StringRes int errorResId);
    }

}
