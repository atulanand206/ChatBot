package com.creations.mvvm.ui.contact;

import android.app.Application;

import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.constants.MvvmConstants;
import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MediatorLiveData;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.ContactProps;
import com.creations.mvvm.ui.FormViewModelBase;
import com.creations.mvvm.ui.editable.EditableViewModel;
import com.creations.mvvm.utils.TransformationsUtils;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.example.application.utils.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

/**
 * This ViewModel works with a Contact and is to be used for creating forms.
 */
public class ContactViewModel extends FormViewModelBase implements ContactContract.ViewModel {

    @NonNull
    private final EditableViewModel mName;

    @NonNull
    private final EditableViewModel mPhone;

    @NonNull
    private final EditableViewModel mEmail;
    @NonNull
    private final MutableLiveData<String> mLabel = new MutableLiveData<>();
    @NonNull
    private final MediatorLiveData<Boolean> mHasError = new MediatorLiveData<>();

    @NonNull
    private final LiveRunnable.Mutable mItemDeleteEvent = new LiveRunnable.Mutable();

    public ContactViewModel(@NonNull final Application application,
                            @NonNull final EditableViewModel.Factory editableFactory) {
        super(application, "Contact items");
        Preconditions.requiresNonNull(editableFactory, "EditableFactory");
        mName = editableFactory.create();
        mPhone = editableFactory.create();
        mEmail = editableFactory.create();

        mName.setAfterTextChangedCallback(this::checkNameError);
        mPhone.setAfterTextChangedCallback(this::checkPhoneError);
        mEmail.setAfterTextChangedCallback(this::checkEmailError);

        TransformationsUtils.addNullableMapSource(mHasError, mName.getTextEnabledError(), error -> isError());
        TransformationsUtils.addNullableMapSource(mHasError, mPhone.getTextEnabledError(), error -> isError());
        TransformationsUtils.addNullableMapSource(mHasError, mEmail.getTextEnabledError(), error -> isError());

    }

    @Override
    public void setDisabled(boolean disabled) {
        super.setDisabled(disabled);
        mName.setDisabled(disabled);
        mPhone.setDisabled(disabled);
        mEmail.setDisabled(disabled);
    }

    private void checkNameError(@Nullable final String name) {
        if (TextUtils.isEmpty(name)) {
            mName.postError(R.string.error_name_mandatory);
        } else {
            mName.removeError();
        }
    }

    private void checkPhoneError(@Nullable final String phone) {
        if (TextUtils.isEmpty(phone) || !phone.matches(MvvmConstants.Regex.PHONE_US)) {
            mPhone.postError(R.string.error_phone_us);
        } else {
            mPhone.removeError();
        }
    }

    private void checkEmailError(@Nullable final String email) {
        if (TextUtils.isEmpty(email) || !email.matches(MvvmConstants.Regex.EMAIL)) {
            mEmail.postError(R.string.error_invalid_email);
        } else {
            mEmail.removeError();
        }
    }

    @Override
    public void postContactProps(@NonNull final ContactProps contactProps) {
        Preconditions.requiresNonNull(contactProps, "ContactProps");
        mName.postText(contactProps.getName());
        mPhone.postText(contactProps.getPhone());
        mEmail.postText(contactProps.getEmail());
        checkNameError(contactProps.getName());
        checkPhoneError(contactProps.getPhone());
        checkEmailError(contactProps.getEmail());
    }

    @NonNull
    @Override
    public EditableViewModel getName() {
        return mName;
    }

    @NonNull
    @Override
    public EditableViewModel getPhone() {
        return mPhone;
    }

    @NonNull
    @Override
    public EditableViewModel getEmail() {
        return mEmail;
    }

    @NonNull
    @Override
    public LiveData<String> getLabel() {
        return mLabel;
    }

    private boolean isError() {
        return mName.hasError() || mPhone.hasError() || mEmail.hasError();
    }

    public LiveData<Boolean> hasError() {
        return mHasError;
    }

    @NonNull
    @Override
    public LiveRunnable getItemDeleteEvent() {
        return mItemDeleteEvent;
    }

    @Override
    public void setPosition(final int position) {
        mLabel.postValue(getApplication().getString(R.string.title_observer, position + 1));
    }

    @Override
    public void deleteObserver() {
        mItemDeleteEvent.postEvent();
    }

    public static class Factory extends MVVMViewModel.Factory<ContactViewModel> {

        @NonNull
        private final EditableViewModel.Factory mEditableFactory;

        public Factory(@NonNull final Application application,
                       @NonNull final EditableViewModel.Factory editableFactory) {
            super(ContactViewModel.class, application);
            mEditableFactory = Preconditions.requiresNonNull(editableFactory, "EditableFactory");
        }

        @NonNull
        @Override
        public ContactViewModel create() {
            return new ContactViewModel(mApplication, mEditableFactory);
        }
    }
}
