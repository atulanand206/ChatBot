package com.creations.mvvm.ui.text;

import android.text.Editable;

import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.IFormViewModelBase;
import com.creations.mvvm.ui.edit.EditContract;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

public interface TextContract {

    interface ViewModel<T extends Props> extends EditContract.ViewModel<T> {

        int getMaxLength();

        @NonNull
        LiveData<Integer> getHeaderVisibility();

        void setHeaderVisibility(int visibility);

        @NonNull
        MutableLiveData<String> getHeader();

        @NonNull
        void setHeader(@NonNull String title);

        @NonNull
        MutableLiveData<String> getSubHeader();

        @NonNull
        void setSubHeader(@NonNull String title);

        @NonNull
        MutableLiveData<String> getTitle();

        @NonNull
        void setTitle(@NonNull String title);

        void setMeaning(@NonNull String title);

        @NonNull
        LiveData<String> getMeaning();

        @NonNull
        LiveData<String> getText();

        void setText(@NonNull String txt);

        void afterTextChanged(@Nullable final Editable editable);

        void setAfterTextChangedCallback(@Nullable IFormViewModelBase.TextChangedCallback callback);

        void setTitleTextSize(@Dimension float textSize);

        LiveData<Float> getTitleTextSize();

        void setTitleTextColorResId(@ColorInt int textColorResId);

        LiveData<Integer> getTitleTextColorResId();

        void setTextSize(@Dimension final float textSize);

        LiveData<Float> getTextSize();

        void setTextColorResId(@ColorInt int textColorResId);

        LiveData<Integer> getTextColorResId();

        void setMaxLength(int size);
    }

}
