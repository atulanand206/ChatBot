package com.creations.mvvm.models.props;

import com.creations.condition.Preconditions;
import com.creations.mvvm.ui.IFormViewModelBase.TextChangedCallback;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * This class is to be used for creating the model for the text view in EditableViewModel.
 */
public class EditableProps extends Props implements Serializable {

    private final String mKey;
    @NonNull
    private final String mText;
    @Nullable
    private final String mPromptText;
    @Nullable
    private final String mHintText;
    private final boolean mDisabled;
    private final boolean mOnlyNumbers;
    private final boolean mMultiLine;
    @Nullable
    private final TextChangedCallback mAfterTextChangedCallback;

    public EditableProps() {
        this(null);
    }

    public EditableProps(@Nullable final String promptText) {
        this(promptText, "", null);
    }

    public EditableProps(final boolean disabled) {
        this("", "", null, null, disabled,
                false, false, null);
    }

    public EditableProps(@Nullable final String promptText,
                         @NonNull final String text,
                         @Nullable final String hintText) {
        this("", text, promptText, hintText, false,
                false, false, null);
    }

    public EditableProps(@NonNull final String key,
                         @NonNull final String text,
                         @Nullable final String promptText,
                         @Nullable final String hintText,
                         final boolean disabled,
                         final boolean onlyNumbers,
                         final boolean multiLine,
                         @Nullable final TextChangedCallback afterTextChangedCallback) {
        mKey = Preconditions.requiresNonNull(key, "Key");
        mText = Preconditions.requiresNonNull(text, "Text");
        mPromptText = promptText;
        mHintText = hintText;
        mDisabled = disabled;
        mOnlyNumbers = onlyNumbers;
        mMultiLine = multiLine;
        mAfterTextChangedCallback = afterTextChangedCallback;
    }

    @NonNull
    public String getKey() {
        return mKey;
    }

    @NonNull
    public String getText() {
        return mText;
    }

    @Nullable
    public String getPromptText() {
        return mPromptText;
    }

    @Nullable
    public String getHintText() {
        return mHintText;
    }

    public boolean isOnlyNumbers() {
        return mOnlyNumbers;
    }

    public boolean isDisabled() {
        return mDisabled;
    }

    public boolean isMultiLine() {
        return mMultiLine;
    }

    @Nullable
    public TextChangedCallback getAfterTextChangedCallback() {
        return mAfterTextChangedCallback;
    }
}
