package com.creations.naina.ui.contact;

import android.app.Application;

import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MediatorLiveData;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.naina.models.ContactProps;
import com.creations.mvvm.ui.FormViewModelBase;
import com.creations.mvvm.ui.editable.EditableViewModel;
import com.creations.mvvm.ui.text.TextViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.example.application.utils.TextUtils;
import com.experiment.billing.constants.Constants;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

/**
 * This ViewModel works with a Contact and is to be used for creating forms.
 */
public class ContactViewModel extends FormViewModelBase implements ContactContract.ViewModel {

    @NonNull
    private final TextViewModel mClient;
    @NonNull
    private final EditableViewModel mName;

    @NonNull
    private final EditableViewModel mEmail;
    @NonNull
    private final MutableLiveData<String> mLabel = new MutableLiveData<>();
    @NonNull
    private final MediatorLiveData<Boolean> mHasError = new MediatorLiveData<>();
    @NonNull
    private final LiveRunnable.Mutable mItemDeleteEvent = new LiveRunnable.Mutable();

    public ContactViewModel(@NonNull final Application application,
                            @NonNull final TextViewModel.Factory textFactory,
                            @NonNull final EditableViewModel.Factory editableFactory) {
        super(application, "Contact items");
        Preconditions.requiresNonNull(editableFactory, "EditableFactory");
        mClient = textFactory.create();
        mClient.setRegex(Constants.REGEX_GSTIN);
        mName = editableFactory.create();
        mEmail = editableFactory.create();
        mContextCallback.addSource(mClient.getContextCallback());
    }

    @NonNull
    @Override
    public TextViewModel getClient() {
        return mClient;
    }

    @Override
    public void setDisabled(boolean disabled) {
        super.setDisabled(disabled);
        mName.setDisabled(disabled);
        mEmail.setDisabled(disabled);
    }

    @Override
    public void postContactProps(@NonNull final ContactProps contactProps,
                                 @NonNull final TextChangedCallback textChangedCallback) {
        Preconditions.requiresNonNull(contactProps, "ContactProps");
        mClient.setHeader(contactProps.getName());
        mClient.setText(contactProps.getGstin());
        mClient.setMeaning(contactProps.getId());
        mClient.setTitle("Enter GSTIN");
        mClient.setAfterTextChangedCallback(textChangedCallback);
    }

    @NonNull
    @Override
    public EditableViewModel getName() {
        return mName;
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
        return mName.hasError() || mEmail.hasError();
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

        @NonNull
        private final TextViewModel.Factory mTextFactory;

        public Factory(@NonNull final Application application,
                       @NonNull final TextViewModel.Factory textFactory,
                       @NonNull final EditableViewModel.Factory editableFactory) {
            super(ContactViewModel.class, application);
            mTextFactory = Preconditions.requiresNonNull(textFactory, "TextFactory");
            mEditableFactory = Preconditions.requiresNonNull(editableFactory, "EditableFactory");
        }

        @NonNull
        @Override
        public ContactViewModel create() {
            return new ContactViewModel(mApplication, mTextFactory, mEditableFactory);
        }
    }
}
