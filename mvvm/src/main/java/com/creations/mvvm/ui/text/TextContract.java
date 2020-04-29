package com.creations.mvvm.ui.text;

import android.text.Editable;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.edit.EditContract;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

public interface TextContract {

    interface ViewModel<T extends Props> extends EditContract.ViewModel<T> {

        @NonNull
        LiveData<String> getText();

        void setText(@NonNull String txt);

        void afterTextChanged(@Nullable final Editable editable);

        void setTextSize(@Dimension final float textSize);

        LiveData<Float> getTextSize();

        void setTextColorResId(@ColorInt int textColorResId);

        LiveData<Integer> getTextColorResId();

    }

}
