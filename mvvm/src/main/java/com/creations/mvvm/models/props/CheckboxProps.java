package com.creations.mvvm.models.props;

import com.creations.condition.Preconditions;
import com.creations.mvvm.ui.editable.EditableViewModel;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CheckboxProps extends Props implements Serializable {

    @NonNull
    private final String mTitle;

    @NonNull
    private final String mMessage;

    @Nullable
    private final EditableViewModel mCustomOptionViewModel;

    private boolean isChecked;

    private boolean isOther;

    public CheckboxProps(@NonNull final String title,
                         @NonNull final String message) {
        this(title, message, false, null);
    }

    public CheckboxProps(@NonNull final String title,
                         @NonNull final String message,
                         @Nullable final EditableViewModel customOptionViewModel) {
        this(title, message, false, customOptionViewModel);
    }

    public CheckboxProps(@NonNull final String title,
                         @NonNull final String message,
                         final boolean isChecked,
                         @Nullable final EditableViewModel customOptionViewModel) {
        mTitle = Preconditions.requiresNonNull(title, "Title");
        mMessage = Preconditions.requiresNonNull(message, "Message");
        this.isChecked = isChecked;
        this.isOther = customOptionViewModel != null;
        mCustomOptionViewModel = customOptionViewModel;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getMessage() {
        return mMessage;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public boolean isOther() {
        return isOther;
    }

    @Nullable
    public EditableViewModel getCustomOptionViewModel() {
        return mCustomOptionViewModel;
    }
}
