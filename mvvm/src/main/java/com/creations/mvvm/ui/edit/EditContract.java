package com.creations.mvvm.ui.edit;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.prop.PropContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public interface EditContract {

    interface ViewModel<T extends Props> extends PropContract.ViewModel<T> {

        @NonNull
        LiveData<Boolean> getEditable();

        void setEditable(final boolean editable);

        void shuffle();

        int getActiveColor();

        void onClick();

        void onClick(@NonNull final Object object);
    }

}
