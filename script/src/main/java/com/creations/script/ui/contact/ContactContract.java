package com.creations.script.ui.contact;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuContract;
import com.creations.script.models.ContactProps;
import com.creations.script.models.State;

public interface ContactContract {

    interface ViewModel<T extends Props> extends MenuContract.ViewModel<T> {

        void postContactProps(@NonNull ContactProps contactProps, TextChangedCallback textChangedCallback);

        LiveData<Boolean> getPreview();

        void setPreview(State state);

        @NonNull
        LiveData<String> getLabel();

        @NonNull
        LiveRunnable getItemDeleteEvent();

        LiveData<Boolean> hasError();

        void deleteObserver();

        void setPosition(final int position);
    }

    interface TextChangedCallback {
        void onTextChanged(@Nullable final String enteredString);
    }


}
