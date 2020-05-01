package com.creations.mvvm.ui.checkbox;

import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.editable.EditableViewModel;
import com.creations.mvvm.ui.menu.MenuContract;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

public interface CheckboxContract {

    interface ViewModel extends MenuContract.ViewModel<Props> {

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
