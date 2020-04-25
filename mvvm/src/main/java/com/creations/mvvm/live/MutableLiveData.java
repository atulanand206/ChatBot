package com.creations.mvvm.live;

import android.os.Looper;

import androidx.annotation.Nullable;

// We can't add in any higher in the inheritance hierarchy since it seems MutableLiveData is
// special cased in the Bindings generation.

/**
 * A {@link androidx.lifecycle.LiveData} object that you can change the value of.
 *
 * Be cautious exposing this in the interface since it will allow anyone with
 * access to set the value, very similar to giving them access to a data field.
 * It is usually preferable to upcast it to a {@link androidx.lifecycle.LiveData<T>}
 * and allow consumers to use that to access the data and provide setter methods
 * if they are needed. The exception to this is for 2-way-bindings with a view
 * where you have to provide Mutable data since you want the view to update it
 * from the user's input.
 *
 * @param <T> The type of data that will be tracked.
 */
public class MutableLiveData<T> extends androidx.lifecycle.MutableLiveData<T> {

    public MutableLiveData() { }

    public MutableLiveData(@Nullable final T initialValue) {
        setValue(initialValue);
    }

    @Override
    public void setValue(@Nullable final T value) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.setValue(value);
        } else {
            super.postValue(value);
        }
    }
}