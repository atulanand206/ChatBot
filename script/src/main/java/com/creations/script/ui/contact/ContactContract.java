package com.creations.script.ui.contact;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuContract;
import com.creations.script.models.ContactProps;

public interface ContactContract {

    interface ViewModel extends MenuContract.ViewModel<Props> {

        void postContactProps(@NonNull ContactProps contactProps, TextChangedCallback textChangedCallback);

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
