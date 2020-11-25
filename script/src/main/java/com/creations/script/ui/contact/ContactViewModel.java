package com.creations.script.ui.contact;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.creations.condition.Preconditions;
import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MediatorLiveData;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.editable.EditableViewModel;
import com.creations.mvvm.ui.menu.MenuViewModel;
import com.creations.mvvm.ui.text.TextViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.creations.script.models.ContactProps;
import com.creations.script.models.State;

/**
 * This ViewModel works with a Contact and is to be used for creating forms.
 */
public class ContactViewModel<T extends Props> extends MenuViewModel<T> implements ContactContract.ViewModel<T> {

    private MutableLiveData<Boolean> mPreview = new MutableLiveData<>(true);

    private ContactProps mContactProps;

    @NonNull
    private final MutableLiveData<String> mLabel = new MutableLiveData<>();
    @NonNull
    private final MediatorLiveData<Boolean> mHasError = new MediatorLiveData<>();
    @NonNull
    private final LiveRunnable.Mutable mItemDeleteEvent = new LiveRunnable.Mutable();

    public ContactViewModel(@NonNull final Application application) {
        super(application, null);
    }

    @Override
    public void postContactProps(@NonNull final ContactProps contactProps,
                                 @NonNull final ContactContract.TextChangedCallback textChangedCallback) {
        Preconditions.requiresNonNull(contactProps, "ContactProps");
        mContactProps = contactProps;
    }

    @Override
    public LiveData<Boolean> getPreview() {
        return mPreview;
    }

    @Override
    public void setPreview(State state) {
        mPreview.setValue(state == State.PREVIEW);
    }

    @NonNull
    @Override
    public LiveData<String> getLabel() {
        return mLabel;
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
        mLabel.postValue(String.valueOf(position + 1));
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
            return new ContactViewModel(mApplication);
        }
    }
}