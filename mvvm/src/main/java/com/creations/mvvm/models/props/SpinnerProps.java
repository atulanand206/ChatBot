package com.creations.mvvm.models.props;

import com.creations.condition.Preconditions;
import com.creations.mvvm.enumerations.SpinnerType;
import com.creations.mvvm.ui.editable.EditableViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class SpinnerProps extends Props implements Serializable {

    @Nullable
    private final String mPrompt;
    @NonNull
    private final String mHint;

    private final int mSelectedIndex;

    @Nullable
    private final EditableViewModel mCustomOptionViewModel;

    private final boolean mIsOtherAvailable;

    @NonNull
    private final List<CharSequence> mEntries;

    @Nullable
    private final SpinnerType mType;

    @NonNull
    private static List<CharSequence> toCharSequenceList(@NonNull final List<Option> spinnerItems) {
        CharSequence[] charSequences = new CharSequence[spinnerItems.size()];
        for (int i = 0; i< spinnerItems.size(); i++) {
            charSequences[i] = spinnerItems.get(i).getValue();
        }
        return Arrays.asList(charSequences);
    }

    private static int selectedIndex(@NonNull final List<Option> spinnerItems,
                                     @NonNull final String value) {
        for (int i=0;i<spinnerItems.size();i++) {
            if (spinnerItems.get(i).getKey().equals(value))
                return i;
        }
        return spinnerItems.size()-1;
    }

    public SpinnerProps(final boolean isOtherAvailable) {
        this(null,"", new ArrayList<>(), "", isOtherAvailable, null, null);
    }

    public SpinnerProps(@Nullable final String prompt,
                        @NonNull final String hint,
                        @NonNull final List<Option> spinnerItems,
                        @NonNull final String value,
                        final boolean isOtherAvailable,
                        @Nullable final EditableViewModel customOptionViewModel,
                        @Nullable final SpinnerType type) {
        this(toCharSequenceList(spinnerItems), prompt, hint,
                selectedIndex(spinnerItems, value), value,
                isOtherAvailable, customOptionViewModel, type);
    }

    public SpinnerProps(@NonNull final List<CharSequence> entries,
                        @Nullable final String prompt,
                        @NonNull final String hint,
                        final int selectedIndex,
                        @NonNull final String otherString,
                        final boolean isOtherAvailable,
                        @Nullable final EditableViewModel customOptionViewModel,
                        @Nullable final SpinnerType type) {
        mPrompt = prompt;
        mHint = Preconditions.requiresNonNull(hint, "Hint");
        String otherValue;
        if (entries.size()-1==selectedIndex)
            otherValue = otherString;
        else
            otherValue = null;
        mSelectedIndex = selectedIndex;
        mIsOtherAvailable = isOtherAvailable;
        mEntries = new ArrayList<>(entries.size() + 2);
        mEntries.add(hint);
        mEntries.addAll(Preconditions.requiresNonNull(entries, "Entries"));
        if (customOptionViewModel != null && otherValue != null)
            customOptionViewModel.setText(otherValue);
        mCustomOptionViewModel = customOptionViewModel;
        mType = type;
    }

    @Nullable
    public String getPrompt() {
        return mPrompt;
    }

    @NonNull
    public String getHint() {
        return mHint;
    }

    public int getSelectedIndex() {
        return mSelectedIndex;
    }

    public boolean isOtherAvailable() {
        return mIsOtherAvailable;
    }

    @NonNull
    public List<CharSequence> getEntries() {
        return mEntries;
    }

    @Nullable
    public EditableViewModel getCustomOptionViewModel() {
        return mCustomOptionViewModel;
    }

    @Nullable
    public SpinnerType getType() {
        return mType;
    }
}
