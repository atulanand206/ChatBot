package com.creations.mvvm.ui.text;

import android.app.Application;
import android.content.Context;
import android.text.Editable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.Dimension;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.lifecycle.LiveData;

import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.IFormViewModelBase.TextChangedCallback;
import com.creations.mvvm.ui.edit.EditViewModel;
import com.example.application.utils.TextUtils;

import static android.widget.LinearLayout.LayoutParams.*;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class TextViewModel<T extends Props> extends EditViewModel<T> implements TextContract.ViewModel<T> {

    @Nullable
    private TextChangedCallback mAfterTextChangedCallback;
    @NonNull
    private final MutableLiveData<String> mTitle = new MutableLiveData<>("");
    @NonNull
    private final MutableLiveData<String> mHeader = new MutableLiveData<>("");
    @NonNull
    private final MutableLiveData<String> mMeaning = new MutableLiveData<>("");
    @NonNull
    private final MutableLiveData<Integer> mHeaderVisibility = new MutableLiveData<>(View.VISIBLE);
    @NonNull
    private final MutableLiveData<String> mSubHeader = new MutableLiveData<>("");
    @NonNull
    private final MutableLiveData<String> mText = new MutableLiveData<>("");
    @NonNull
    private MutableLiveData<Float> titleTextSize = new MutableLiveData<>();
    @NonNull
    private MutableLiveData<Integer> titleTextColorResId = new MutableLiveData<>();

    private MutableLiveData<Float> textSize = new MutableLiveData<>();

    private MutableLiveData<Integer> textColorResId = new MutableLiveData<>();

    private int maxLength = 0;

    public TextViewModel(@NonNull final Application application,
                         @NonNull final T props) {
        super(application, props);

    }

    @NonNull
    @Override
    public LiveData<Integer> getHeaderVisibility() {
        return mHeaderVisibility;
    }

    @Override
    public void setHeaderVisibility(final int visibility) {
        mHeaderVisibility.postValue(visibility);
    }

    @NonNull
    @Override
    public MutableLiveData<String> getHeader() {
        return mHeader;
    }

    @Override
    public void setHeader(@NonNull final String title) {
        mHeader.postValue(title);
    }

    @NonNull
    @Override
    public MutableLiveData<String> getSubHeader() {
        return mSubHeader;
    }

    @Override
    public void setSubHeader(@NonNull final String title) {
        mSubHeader.postValue(title);
    }

    @NonNull
    @Override
    public MutableLiveData<String> getTitle() {
        return mTitle;
    }

    @Override
    public void setTitle(@NonNull final String title) {
        mTitle.postValue(title);
    }

    @Override
    public void setMeaning(@NonNull final String title) {
        mMeaning.postValue(title);
    }
    @NonNull

    @Override
    public LiveData<String> getMeaning() {
        return mMeaning;
    }

    @NonNull
    @Override
    public LiveData<String> getText() {
        return mText;
    }

    @Override
    public void setText(@NonNull final String txt) {
        mText.postValue(txt);
        if (mAfterTextChangedCallback != null) {
            mAfterTextChangedCallback.onTextChanged(txt);
        }
    }

    @Override
    public void afterTextChanged(@Nullable final Editable editable) {
        String text = null;
        if (editable != null) {
            text = editable.toString();
        }
        if (TextUtils.isEmpty(text)) {
            text = null;
        }
        mText.setValue(text);
    }

    @Override
    public void setAfterTextChangedCallback(@Nullable final TextChangedCallback callback) {
        mAfterTextChangedCallback = callback;
    }

    private String regex = "";
    private boolean mDialogShowing = false;

    public void setRegex(final String regex) {
        this.regex = regex;
    }

    @Override
    public void onClick() {
        mDialogShowing = true;
        mContextCallback.postEvent(this::showEditDialog);
    }

    private void showEditDialog(Context context) {
        if (!mDialogShowing)
            return;
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context, R.style.App_Component_Dialog_Alert);
        String header = getHeader().getValue();
        alertDialog.setTitle(header);
        String title = getTitle().getValue();
        alertDialog.setMessage("Enter " + (title == null ? header : title));
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.dialog_success, null);
        final EditText input = view.findViewById(R.id.text_view);
        input.setText(getText().getValue());
        alertDialog.setView(view);
        alertDialog.setPositiveButton("CHANGE",
                (dialog, which) -> {
                    setEditedText(context, input);
                    mDialogShowing = false;
                });
        alertDialog.setNegativeButton("CANCEL",
                (dialog, which) -> {
                    dialog.cancel();
                    mDialogShowing = false;
                });
        alertDialog.show();
    }

    private void setEditedText(Context context, EditText input) {
        String txt = input.getText().toString();
        if (txt.isEmpty())
            Toast.makeText(context, "Invalid Entry. Try again.", Toast.LENGTH_SHORT).show();
        else if (regex != null && !regex.isEmpty()) {
            if (!txt.matches(regex))
                Toast.makeText(context, "Invalid Entry. Try again.", Toast.LENGTH_SHORT).show();
            else
                setText(txt);
        } else
            setText(txt);
    }

    @Override
    public void setTitleTextSize(@Dimension float textSize) {
        this.titleTextSize.postValue(textSize);
    }

    @Override
    public LiveData<Float> getTitleTextSize() {
        return titleTextSize;
    }

    @Override
    public void setTitleTextColorResId(@ColorInt int textColorResId) {
        this.titleTextColorResId.postValue(textColorResId);
    }

    @Override
    public LiveData<Integer> getTitleTextColorResId() {
        return titleTextColorResId;
    }

    @Override
    public void setTextSize(@Dimension float textSize) {
        this.textSize.postValue(textSize);
    }

    @Override
    public LiveData<Float> getTextSize() {
        return textSize;
    }

    @Override
    public void setTextColorResId(@ColorInt int textColorResId) {
        this.textColorResId.postValue(textColorResId);
    }

    @Override
    public LiveData<Integer> getTextColorResId() {
        return textColorResId;
    }

    @Override
    public int getMaxLength() {
        return maxLength;
    }

    @Override
    public void setMaxLength(int size) {
        maxLength = size;
    }

    public static class Factory<T extends Props> extends EditViewModel.Factory<T> {

        @NonNull
        private final T mProps;

        public Factory(@NonNull final Application application,
                       @NonNull final T props) {
            super(application, props);
            mProps = Preconditions.requiresNonNull(props, "Props");
        }

        @NonNull
        @Override
        public TextViewModel create() {
            return new TextViewModel<>(mApplication, mProps);
        }
    }
}
