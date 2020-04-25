package com.creations.mvvm.form.checkbox;

import com.creations.mvvm.form.editable.EditableViewModel;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.viewmodel.IMVVMViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

public interface CheckboxContract {

    interface ViewModel extends IMVVMViewModel {

        /**
         * @return liveData corresponding to the text.
         */
        @NonNull
        LiveData<String> getTitle();

        /**
         * @return true if title is to be displayed.
         */
        @NonNull
        MutableLiveData<Integer> getTitleVisibility();

        /**
         * @return liveData corresponding to the prompt text.
         */
        @NonNull
        LiveData<String> getMessage();

        /**
         * @return liveData corresponding to the checkbox.
         */
        @NonNull
        LiveData<Boolean> isChecked();

        /**
         * @return true if the checkbox item is checked.
         */
        boolean isCheckboxSelected();

        /**
         * Called when the checklist item is tapped.
         */
        void onItemClick();

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

    }

}
