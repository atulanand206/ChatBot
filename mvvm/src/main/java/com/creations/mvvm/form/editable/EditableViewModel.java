package com.creations.mvvm.form.editable;

import android.app.Application;
import android.text.Editable;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.view.View;

import com.creations.condition.Preconditions;
import com.creations.mvvm.form.FormViewModelBase;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.EditableProps;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.example.application.utils.PhoneSpan;
import com.example.application.utils.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

import static com.example.application.constants.ApplicationConstants.Phone.COUNTRY_CODE;


/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class EditableViewModel extends FormViewModelBase implements EditableContract.ViewModel {

    @Nullable
    private TextChangedCallback mAfterTextChangedCallback;

    @NonNull
    private final MutableLiveData<String> mHintText = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<String> mPromptText = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Boolean> mTextEnabledError = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Integer> mTextColor = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<String> mTextError = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Integer> mFieldVisibility = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Integer> mTextLength = new MutableLiveData<>();
    @NonNull
    private final LiveData<Boolean> mClearVisibility = Transformations.map(mText, text -> !TextUtils.isEmpty(text));
    @NonNull
    private final MutableLiveData<Integer> mInputType = new MutableLiveData<>();

    public EditableViewModel(@NonNull final Application application,
                                @NonNull final EditableProps editableProps) {
        super(application, editableProps.getText());
        Preconditions.requiresNonNull(editableProps, "EditableProps");

        mPromptText.postValue(editableProps.getPromptText());
        mHintText.postValue(editableProps.getHintText());
        mDisabled.postValue(editableProps.isDisabled());
        if (editableProps.isMultiLine()) {
            mInputType.postValue(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        } else if (editableProps.isOnlyNumbers()) {
            mInputType.postValue(InputType.TYPE_CLASS_NUMBER);
        } else {
            mInputType.postValue(InputType.TYPE_CLASS_TEXT);
        }
        mAfterTextChangedCallback = editableProps.getAfterTextChangedCallback();
    }

    @NonNull
    @Override
    public LiveData<String> getPromptText() {
        return mPromptText;
    }

    @NonNull
    @Override
    public LiveData<String> getHintText() {
        return mHintText;
    }

    @NonNull
    @Override
    public LiveData<Boolean> getTextEnabledError() {
        return mTextEnabledError;
    }


    @NonNull
    @Override
    public LiveData<Integer> getTextColor() {
        return mTextColor;
    }

    @NonNull
    @Override
    public LiveData<String> getTextError() {
        return mTextError;
    }

    @NonNull
    @Override
    public MutableLiveData<Integer> getFieldVisibility() {
        return mFieldVisibility;
    }

    @NonNull
    @Override
    public LiveData<Boolean> getClearVisibility() {
        return mClearVisibility;
    }

    @NonNull
    @Override
    public LiveData<Integer> getInputType() {
        return mInputType;
    }

    @Override
    public void afterTextChangedText(@Nullable final Editable editable) {
        String text = null;
        if (editable != null) {
            text = editable.toString();
        }
        if (TextUtils.isEmpty(text)) {
            text = null;
        }
        Integer inputType = mInputType.getValue();
        if (inputType != null && inputType.equals(InputType.TYPE_CLASS_PHONE))
            afterTextChangedPhone(editable);
        else
            mText.setValue(text);
        if (mAfterTextChangedCallback != null) {
            mAfterTextChangedCallback.onTextChanged(text);
        }
    }

    @Override
    public void onFocusChange(@NonNull final View view, final boolean isFocused) {
//        mClearVisibility.postValue(isFocused);
    }

    @Override
    public void setAfterTextChangedCallback(@Nullable final TextChangedCallback callback) {
        mAfterTextChangedCallback = callback;
    }

    @Override
    public void setVisibility(final boolean visible) {
        mFieldVisibility.postValue(visible ? View.VISIBLE : View.GONE);
    }


    @Override
    public void setTextColor(int colorResId) {
        mTextColor.postValue(colorResId);
    }


    @Override
    public boolean hasError() {
        return mTextEnabledError.getValue() == null || mTextEnabledError.getValue();
    }

    @Override
    public void postError(final int stringResId) {
        postError(mTextError, mTextEnabledError, stringResId);
    }

    @Override
    public void removeError() {
        removePostError(mTextError, mTextEnabledError);
    }

    @Override
    public void postPromptText(final String promptText) {
        mPromptText.postValue(promptText);
    }

    @Override
    public void postHintText(final String hintText) {
        mHintText.postValue(hintText);
    }

    @Override
    public void clearText() {
        mText.postValue(null);
    }

    @Override
    public void setInputType(int type) {
        mInputType.postValue(type);
        if (type == InputType.TYPE_CLASS_PHONE)
            setTextLength(12);
    }

    @Override
    public LiveData<Integer> getTextLength() {
        return mTextLength;
    }

    @Override
    public void setTextLength(int length) {
        mTextLength.postValue(length);
    }

    private int phoneEditStartIndex = -1;
    private int phoneEditStopIndex = -1;
    @Override
    public void onTextChangedPhone(@Nullable final CharSequence charSequence,
                                   int start, int before, int count) {
        Integer inputTypeValue = mInputType.getValue();
        if (inputTypeValue == null)
            return;
        if (!inputTypeValue.equals(InputType.TYPE_CLASS_PHONE))
            return;
        if (charSequence != null && !charSequence.toString().equals(mText.getValue())) {
            int phoneEditOffset = 0;
            String newPhoneNumber = charSequence.toString();
            String oldPhoneNumber = mText.getValue();
            if (oldPhoneNumber == null) {
                oldPhoneNumber = "";
            }
            if (count > 10) {
                phoneEditOffset -= newPhoneNumber.length() - 10;
                newPhoneNumber = newPhoneNumber.substring(newPhoneNumber.length() - 10);
            }
            if (start < COUNTRY_CODE.length() && oldPhoneNumber.startsWith(COUNTRY_CODE)) {
                if (start == 0 && before < COUNTRY_CODE.length()) {
                    for (int index = 1; index < COUNTRY_CODE.length(); index++) {
                        if (newPhoneNumber.startsWith(COUNTRY_CODE.substring(index))) {
                            newPhoneNumber = COUNTRY_CODE + newPhoneNumber.substring(COUNTRY_CODE.length() - index);
                            phoneEditOffset += index;
                            break;
                        }
                    }
                } else if (newPhoneNumber.startsWith(COUNTRY_CODE.substring(0, 1))) {
                    for (int index = 1; index < COUNTRY_CODE.length(); index++) {
                        if (!newPhoneNumber.startsWith(COUNTRY_CODE.substring(0, index + 1))) {
                            newPhoneNumber = COUNTRY_CODE + newPhoneNumber.substring(index);
                            phoneEditOffset += COUNTRY_CODE.length() - index;
                            break;
                        }
                    }
                }
            }
            if(!TextUtils.isEmpty(newPhoneNumber)) {
                if (!newPhoneNumber.startsWith(COUNTRY_CODE)) {
                    newPhoneNumber = COUNTRY_CODE + newPhoneNumber;
                    phoneEditOffset += COUNTRY_CODE.length();
                }
            }
            if (charSequence instanceof Spannable) {
                phoneEditStartIndex = Selection.getSelectionStart(charSequence) + phoneEditOffset;
                phoneEditStopIndex = Selection.getSelectionEnd(charSequence) + phoneEditOffset;
                phoneEditStartIndex = Math.max(phoneEditStartIndex, 0);
                phoneEditStopIndex = Math.max(phoneEditStopIndex, 0);
            } else {
                phoneEditStartIndex = -1;
                phoneEditStopIndex = -1;
            }
            // Cannot do a setValue because we have to set it after the view finishes the text change.
            mText.postValue(newPhoneNumber);
        }
    }

    @Override
    public void afterTextChangedPhone(@Nullable final Editable editable) {
        Integer inputTypeValue = mInputType.getValue();
        if (inputTypeValue == null)
            return;
        if (!inputTypeValue.equals(InputType.TYPE_CLASS_PHONE))
            return;
        if (editable != null) {
            PhoneSpan.Factory factory = new PhoneSpan.Factory();
            factory.spacedEditable(editable);
            String newValue = editable.toString();
            if (editable.toString().equals(mText.getValue())
                    && phoneEditStartIndex >= 0 && phoneEditStopIndex >= 0
                    && phoneEditStartIndex <= newValue.length() && phoneEditStopIndex <= newValue.length()) {
                Selection.setSelection(editable, phoneEditStartIndex, phoneEditStopIndex);
            }
        }
    }

    public static class Factory extends MVVMViewModel.Factory<EditableViewModel> {

        @NonNull
        private final EditableProps mEditableProps;

        public Factory(@NonNull final Application application,
                       @NonNull final EditableProps editableProps) {
            super(EditableViewModel.class, application);
            mEditableProps = Preconditions.requiresNonNull(editableProps, "EditableProps");
        }

        @NonNull
        @Override
        public EditableViewModel create() {
            return new EditableViewModel(mApplication, mEditableProps);
        }
    }
}
