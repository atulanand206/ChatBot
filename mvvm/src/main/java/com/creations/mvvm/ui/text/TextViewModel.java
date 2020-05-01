package com.creations.mvvm.ui.text;

import android.app.Application;
import android.text.Editable;
import android.view.View;

import com.creations.condition.Preconditions;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.edit.EditViewModel;
import com.example.application.utils.TextUtils;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class TextViewModel<T extends Props> extends EditViewModel<T> implements TextContract.ViewModel<T> {

    @NonNull
    private final MutableLiveData<String> mTitle = new MutableLiveData<>("");
    @NonNull
    private final MutableLiveData<String> mHeader = new MutableLiveData<>("");
    @NonNull
    private final MutableLiveData<String> mMeaning = new MutableLiveData<>("");
    @NonNull
    private final MutableLiveData<Integer> mHeaderVisibility = new MutableLiveData<>(View.GONE);
    @NonNull
    private final MutableLiveData<String> mSubHeader = new MutableLiveData<>("");
    @NonNull
    private final MutableLiveData<String> mText = new MutableLiveData<>("");
    @NonNull
    private MutableLiveData<Float> titleTextSize = new MutableLiveData<>();
    @NonNull
    private MutableLiveData<Integer> titleTextColorResId = new MutableLiveData<>();

    private MutableLiveData<Float> textSize = new MutableLiveData<>();

    private MutableLiveData<Integer> textColorResId = new MutableLiveData<>();

    public TextViewModel(@NonNull final Application application,
                         @NonNull final T props) {
        super(application, props);

    }

    @NonNull
    @Override
    public MutableLiveData<Integer> getHeaderVisibility() {
        return mHeaderVisibility;
    }

    @Override
    public void setHeaderVisibility(final int visibility) {
        mHeaderVisibility.postValue(visibility);
    }

    @NonNull
    @Override
    public MutableLiveData<String> getHeader() {
        return mHeader;
    }

    @Override
    public void setHeader(@NonNull final String title) {
        mHeader.postValue(title);
    }

    @NonNull
    @Override
    public MutableLiveData<String> getSubHeader() {
        return mSubHeader;
    }

    @Override
    public void setSubHeader(@NonNull final String title) {
        mSubHeader.postValue(title);
    }

    @NonNull
    @Override
    public MutableLiveData<String> getTitle() {
        return mTitle;
    }

    @Override
    public void setTitle(@NonNull final String title) {
        mTitle.postValue(title);
    }

    @Override
    public void setMeaning(@NonNull final String title) {
        mMeaning.postValue(title);
    }
    @NonNull

    @Override
    public LiveData<String> getMeaning() {
        return mMeaning;
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

    @Override
    public void setTitleTextSize(@Dimension float textSize) {
        this.titleTextSize.postValue(textSize);
    }

    @Override
    public LiveData<Float> getTitleTextSize() {
        return titleTextSize;
    }

    @Override
    public void setTitleTextColorResId(@ColorInt int textColorResId) {
        this.titleTextColorResId.postValue(textColorResId);
    }

    @Override
    public LiveData<Integer> getTitleTextColorResId() {
        return titleTextColorResId;
    }

    @Override
    public void setTextSize(@Dimension float textSize) {
        this.textSize.postValue(textSize);
    }

    @Override
    public LiveData<Float> getTextSize() {
        return textSize;
    }

    @Override
    public void setTextColorResId(@ColorInt int textColorResId) {
        this.textColorResId.postValue(textColorResId);
    }

    @Override
    public LiveData<Integer> getTextColorResId() {
        return textColorResId;
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
