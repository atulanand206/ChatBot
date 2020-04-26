package com.creations.mvvm.ui;

import android.app.Application;
import android.text.TextUtils;

import com.creations.condition.Preconditions;
import com.creations.mvvm.live.MediatorLiveData;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class FormViewModelBase extends MVVMViewModel implements IFormViewModelBase {

    @NonNull
    protected final MutableLiveData<String> mText = new MutableLiveData<>("");

    @NonNull
    protected final MediatorLiveData<Boolean> mDisabled = new MediatorLiveData<>(null);

    protected FormViewModelBase(@NonNull Application application,
                                @NonNull String text) {
        super(application);
        mText.postValue(Preconditions.requiresNonNull(text, "Text"));
    }

    @NonNull
    @Override
    public MutableLiveData<String> getText() {
        return mText;
    }

    @NonNull
    @Override
    public MediatorLiveData<Boolean> isDisabled() {
        return mDisabled;
    }

    @Override
    public void setDisabled(final boolean disabled) {
        mDisabled.postValue(disabled);
    }

    @NonNull
    @Override
    public String getEnteredText() {
        String text = mText.getValue();
        if (text == null) {
            return "";
        } else {
            return text.trim();
        }
    }

    @Override
    public void setText(@Nullable String text) {
        if (TextUtils.isEmpty(text)) {
            mText.setValue(null);
        } else {
            mText.setValue(text);
        }
    }

    @Override
    public void postText(@Nullable final String text) {
        if (TextUtils.isEmpty(text)) {
            mText.postValue(null);
        } else {
            mText.postValue(text);
        }
    }

}
