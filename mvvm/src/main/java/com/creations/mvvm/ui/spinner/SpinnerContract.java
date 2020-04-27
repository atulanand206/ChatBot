package com.creations.mvvm.ui.spinner;

import android.widget.AdapterView;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.IFormViewModelBase;
import com.creations.mvvm.ui.editable.EditableViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

public interface SpinnerContract {

    interface ViewModel extends IFormViewModelBase<Props> {

        /**
         * @return liveData corresponding to the list of entries.
         */
        @NonNull
        LiveData<AdapterView.OnItemSelectedListener> getItemSelectedListener();

        /**
         * @return liveData corresponding to the sequence of entries.
         */
        @NonNull
        LiveData<CharSequence[]> getEntries();

        @NonNull
        String getSelectedEntry();

        /**
         * set liveData corresponding to the sequence of entries.
         */
        @NonNull
        void postEntries(CharSequence[] entries);

        /**
         * @return the liveData for the prompt.
         */
        @NonNull
        LiveData<String> getPrompt();

        void setPrompt(String prompt);

        /**
         * @return the liveData for the hint.
         */
        @NonNull
        LiveData<String> getHint();

        /**
         * @return the liveData for the selected item index.
         */
        @NonNull
        LiveData<Integer> getSelectedIndex();

        /**
         * @return the liveData for the selected item's position in entries.
         */
        @NonNull
        LiveData<Integer> getPosition();

        /**
         * @return the liveData for the visibility of describe field.
         */
        @NonNull
        LiveData<Integer> getDescribeVisibility();

        /**
         * @return the EditableViewModel for adding a Custom Option. Can be null if it doesn't allow Custom Options.
         */
        @Nullable
        EditableViewModel getCustomOptionViewModel();

        @Nullable
        EditableViewModel getCustomOptionViewModelManual();

        void postIndex(final int index);

        void hideDescribeView();

        LiveData<String> getManualText();
    }

}
