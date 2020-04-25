package com.creations.mvvm.form.contact;

import com.creations.mvvm.form.IFormViewModelBase;
import com.creations.mvvm.form.editable.EditableViewModel;
import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.models.props.ContactProps;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public interface ContactContract {

    interface ViewModel extends IFormViewModelBase {

        void postContactProps(@NonNull ContactProps contactProps);

        @NonNull
        EditableViewModel getName();

        @NonNull
        EditableViewModel getPhone();

        @NonNull
        EditableViewModel getEmail();

        @NonNull
        LiveData<String> getLabel();

        @NonNull
        LiveRunnable getItemDeleteEvent();

        LiveData<Boolean> hasError();

        void deleteObserver();

        void setPosition(final int position);
    }

}
