package com.creations.mvvm.ui.editable;

import android.text.Editable;
import android.view.View;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.IFormViewModelBase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

public interface EditableContract {

    interface ViewModel extends IFormViewModelBase<Props> {

        /**
         * @return liveData corresponding to the prompt text.
         */
        @NonNull
        LiveData<String> getPromptText();

        /**
         * @return liveData corresponding to the hint text in the editable.
         */
        @NonNull
        LiveData<String> getHintText();

        /**
         * @return liveData corresponding to the enabled or disabled error in text.
         */
        @NonNull
        LiveData<Boolean> getTextEnabledError();


        @NonNull
        LiveData<Integer> getTextColor();

        /**
         * @return liveData corresponding to the error in text.
         */
        @NonNull
        LiveData<String> getTextError();

        /**
         * @return liveData corresponding to the visibility of the field.
         */
        @NonNull
        LiveData<Integer> getFieldVisibility();

        /**
         * @return A LiveData object that will contain whether the clear button should
         * be visible, updated as needed.
         */
        @NonNull
        LiveData<Boolean> getClearVisibility();

        /**
         * @return liveData corresponding to the input type.
         */
        @NonNull
        LiveData<Integer> getInputType();

        /**
         * This is called when the text has been changed in the text field.
         * @param editable charsequence information about the entered text.
         */
        void afterTextChangedText(@Nullable final Editable editable);

        void afterTextChangedPhone(@Nullable final Editable editable);

        void onTextChangedPhone(@Nullable final CharSequence charSequence,
                                int start, int before, int count);

        /**
         * @param view text view whose focus is to be considered.
         * @param isFocused true if the view is brought into focus.
         */
        void onFocusChange(@NonNull final View view, final boolean isFocused);

        void setAfterTextChangedCallback(@Nullable TextChangedCallback callback);

        /**
         * @param visible true if the view should be made visible, else false.
         */
        void setVisibility(boolean visible);

        void setTextColor(final int colorResId);


        boolean hasError();

        /**
         * @param stringResId id of string resource to be shown as error in the text field.
         */
        void postError(final int stringResId);

        /**
         * remove the error attached in the text field.
         */
        void removeError();

        /**
         * @param promptText to be shown as label in altitude.
         */
        void postPromptText(final String promptText);

        /**
         * @param hintText to be shown as hint in altitude.
         */
        void postHintText(final String hintText);

        /**
         * This is called when text editable is to be cleared out.
         */
        void clearText();

        void setInputType(final int type);

        void setTextLength(final int length);

        @NonNull
        LiveData<Integer> getTextLength();

    }

}
