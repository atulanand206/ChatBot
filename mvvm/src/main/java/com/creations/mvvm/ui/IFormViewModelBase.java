package com.creations.mvvm.ui;

import com.creations.mvvm.viewmodel.IMVVMViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

public interface IFormViewModelBase extends IMVVMViewModel {

    /**
     * @return liveData on whether the form field is disabled from receiving input.
     */
    @NonNull
    LiveData<Boolean> isDisabled();

    /**
     * @return liveData corresponding to the text.
     */
    @NonNull
    LiveData<String> getText();

    /**
     * @return the string entered in the form field.
     */
    @NonNull
    String getEnteredText();

    /**
     * Helper method to post to the Text LiveData.
     */
    void postText(@NonNull final String text);

    /**
     * Helper method to set to the Text LiveData.
     */
    void setText(@NonNull final String text);

    /**
     * @param disabled true if the view should not have the ability to focus, else false.
     */
    void setDisabled(boolean disabled);

    public interface TextChangedCallback {
        void onTextChanged(@Nullable final String enteredString);
    }

}
