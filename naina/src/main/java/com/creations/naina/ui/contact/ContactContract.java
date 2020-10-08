package com.creations.naina.ui.contact;

import com.creations.mvvm.live.LiveRunnable;
import com.creations.naina.models.ContactProps;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.IFormViewModelBase;
import com.creations.mvvm.ui.editable.EditableViewModel;
import com.creations.mvvm.ui.text.TextViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public interface ContactContract {

    interface ViewModel extends IFormViewModelBase<Props> {

        void postContactProps(@NonNull ContactProps contactProps, TextChangedCallback textChangedCallback);

        @NonNull
        TextViewModel getClient();

        @NonNull
        EditableViewModel getName();

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
