package com.creations.mvvm.ui.checkbox;

import android.app.Application;
import android.view.View;

import com.creations.condition.Preconditions;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.CheckboxProps;
import com.creations.mvvm.ui.FormViewModelBase;
import com.creations.mvvm.ui.editable.EditableViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.example.application.utils.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

/**
 * This ViewModel works with a Checklist and is to be used for creating forms.
 */
public class CheckboxViewModel extends FormViewModelBase implements CheckboxContract.ViewModel {

    @NonNull
    private MutableLiveData<String> mTitle = new MutableLiveData<>();

    @NonNull
    private MutableLiveData<Integer> mTitleVisibility = new MutableLiveData<>();

    @NonNull
    private MutableLiveData<String> mMessage = new MutableLiveData<>();

    @NonNull
    private MutableLiveData<Boolean> mChecked = new MutableLiveData<>(false);

    @NonNull
    private MutableLiveData<Boolean> mIsOther = new MutableLiveData<>(false);

    @NonNull
    private MutableLiveData<Integer> mDescribeVisibility = new MutableLiveData<>();

    @Nullable
    private final EditableViewModel mCustomOptionViewModel;

    public CheckboxViewModel(@NonNull final Application application,
                             @NonNull final CheckboxProps checkboxProps) {
        super(application, "Checklist items");
        Preconditions.requiresNonNull(checkboxProps, "checkboxProps");
        mTitle.postValue(checkboxProps.getTitle());
        if (TextUtils.isEmpty(checkboxProps.getTitle()))
            mTitleVisibility.postValue(View.GONE);
        else
            mTitleVisibility.postValue(View.VISIBLE);
        mMessage.postValue(checkboxProps.getMessage());
        mChecked.postValue(checkboxProps.isChecked());
        mIsOther.postValue(checkboxProps.isOther());
        mDescribeVisibility.postValue(checkboxProps.isOther() && checkboxProps.isChecked() ? View.VISIBLE : View.GONE);
        mCustomOptionViewModel = checkboxProps.getCustomOptionViewModel();
    }

    @NonNull
    @Override
    public LiveData<String> getTitle() {
        return mTitle;
    }

    @NonNull
    @Override
    public MutableLiveData<Integer> getTitleVisibility() {
        return mTitleVisibility;
    }

    @NonNull
    @Override
    public LiveData<String> getMessage() {
        return mMessage;
    }

    @NonNull
    @Override
    public LiveData<Boolean> isChecked() {
        return mChecked;
    }

    @Override
    public boolean isCheckboxSelected() {
        Boolean checkedValue = mChecked.getValue();
        if (checkedValue != null)
            return checkedValue;
        return false;
    }

    @Override
    public void onItemClick() {
        Boolean value = mChecked.getValue();
        if (value != null) {
            mChecked.postValue(!value);
        } else {
            mChecked.postValue(true);
        }
        Boolean isOther = mIsOther.getValue();
        if (isOther != null && isOther) {
            if (value != null)
                mDescribeVisibility.postValue(value ? View.GONE : View.VISIBLE);
            else
                mDescribeVisibility.postValue(View.VISIBLE);
        }

    }

    @NonNull
    @Override
    public LiveData<Integer> getDescribeVisibility() {
        return mDescribeVisibility;
    }

    @Nullable
    @Override
    public EditableViewModel getCustomOptionViewModel() {
        return mCustomOptionViewModel;
    }

    public static class Factory extends MVVMViewModel.Factory<CheckboxViewModel> {

        @NonNull
        private final CheckboxProps mCheckboxProps;

        public Factory(@NonNull final Application application,
                       @NonNull final CheckboxProps CheckboxProps) {
            super(CheckboxViewModel.class, application);
            mCheckboxProps = Preconditions.requiresNonNull(CheckboxProps, "CheckboxProps");
        }

        @NonNull
        @Override
        public CheckboxViewModel create() {
            return new CheckboxViewModel(mApplication, mCheckboxProps);
        }
    }
}
