package com.creations.mvvm.form.spinner;

import android.app.Application;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.enumerations.SpinnerType;
import com.creations.mvvm.form.FormViewModelBase;
import com.creations.mvvm.form.editable.EditableViewModel;
import com.creations.mvvm.live.MediatorLiveData;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.SpinnerProps;
import com.creations.mvvm.utils.TransformationsUtils;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * This ViewModel works with a spinner and is to be used for creating forms.
 */
public class SpinnerViewModel extends FormViewModelBase implements SpinnerContract.ViewModel {
    @NonNull
    private final Application mApplication;

    @NonNull
    private final MutableLiveData<AdapterView.OnItemSelectedListener> mItemSelectedListener = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<CharSequence[]> mEntries = new MutableLiveData<>();
    @NonNull
    private final MediatorLiveData<String> mManualText = new MediatorLiveData<>();
    @NonNull
    private final MutableLiveData<String> mPrompt = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<String> mHint = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Boolean> mIsOtherAvailable = new MutableLiveData<>(false);
    @NonNull
    private final MutableLiveData<Integer> mSelectedIndex = new MutableLiveData<>(-1);
    @NonNull
    private final MutableLiveData<Integer> mDescribeVisibility = new MutableLiveData<>(GONE);
    @NonNull
    private final MutableLiveData<Integer> mPosition = new MutableLiveData<>(0);
    @Nullable
    private EditableViewModel mCustomOptionViewModel;
    @Nullable
    private EditableViewModel mCustomOptionViewModelManual;
    @NonNull
    private SpinnerType mSpinnerType = SpinnerType.CHECKLIST;

    public SpinnerViewModel(@NonNull final Application application,
                            @NonNull final SpinnerProps spinnerProps) {
        super(application, "spinner items");
        mApplication = application;
        Preconditions.requiresNonNull(spinnerProps, "spinnerProps");
        List<CharSequence> propsEntries = spinnerProps.getEntries();
        CharSequence[] entries = new CharSequence[propsEntries.size()];
        propsEntries.toArray(entries);
        mEntries.postValue(entries);

        mPrompt.postValue(spinnerProps.getPrompt());
        mHint.postValue(spinnerProps.getHint());

        mIsOtherAvailable.postValue(spinnerProps.isOtherAvailable());
        mCustomOptionViewModel = spinnerProps.getCustomOptionViewModel();
        addTransformations();

        mItemSelectedListener.postValue(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(@Nullable final AdapterView<?> parent, @Nullable final View view,
                                       final int position, final long id) {
                selectNewItem(view, position);
                fixColors(view, position);
            }

            private void fixColors(@Nullable final View view, final int position) {
                TextView textView = getHeadView(view);
                if (textView != null) {
                    if (position == 0)
                        textView.setTextColor(mApplication.getColor(R.color.grey_hint));
                    else
                        textView.setTextColor(mApplication.getColor(R.color.black));
                }
            }

            @Nullable
            private TextView getHeadView(@Nullable final View view) {
                if (view == null)
                    return null;
                View viewById = view.findViewById(android.R.id.text1);
                if (viewById instanceof TextView) {
                    return (TextView) viewById;
                }
                return null;
            }

            private void selectNewItem(@Nullable final View view, final int position) {

                CharSequence[] currentEntries = mEntries.getValue();
                Preconditions.verifyNonNull(currentEntries, "Entries");
                switch (mSpinnerType) {
                    case COA: {
                        toggleDescribe(position != 0);
                        toggleCustomEditableViewModel(position != 0);
                        enableCustomEditableViewModel(position == currentEntries.length - 1);
                        if (position == currentEntries.length - 1 || position == 0) {
                            if (mCustomOptionViewModelManual != null) {
                                mCustomOptionViewModelManual.postText(null);
                                mCustomOptionViewModelManual.setAfterTextChangedCallback(mManualText::postValue);
                            }

                        } else {
                            String[] coaNamesAndNumbers = currentEntries[position].toString().split("[\\(\\)]");
                            if(coaNamesAndNumbers.length > 1) {
                                if (mCustomOptionViewModel != null) {
                                    mCustomOptionViewModel.postText(coaNamesAndNumbers[1]);
                                    mManualText.postValue(coaNamesAndNumbers[1]);
                                }
                            }
                            TextView textView = getHeadView(view);
                            if (textView != null) {
                                textView.setText(coaNamesAndNumbers[0]);
                            }
                        }
                        break;}
                    case CHECKLIST:
                        Boolean valueObject = mIsOtherAvailable.getValue();
                        boolean value = false;
                        if (valueObject != null) {
                            value = valueObject;
                        }
                        toggleDescribe(value && position == currentEntries.length - 1);
                        break;
                    case TYPE_OF_EVENT:
                        if (position == currentEntries.length - 1 && mCustomOptionViewModel != null) {
                            mText.postValue(mCustomOptionViewModel.getEnteredText());
                        } else if (position != 0) {
                            mText.postValue(currentEntries[position - 1].toString());
                        } else {
                            mText.postValue(null);
                        }

                        break;
                }
                mSelectedIndex.postValue(position);
                mPosition.postValue(position);
            }

            @Override
            public void onNothingSelected(@Nullable final AdapterView<?> parent) { }
        });
        if (mSpinnerType == SpinnerType.CHECKLIST)
            postIndex(spinnerProps.getSelectedIndex() + 1);
    }

    private void toggleDescribe(final boolean show) {
        if (show) {
            mDescribeVisibility.postValue(VISIBLE);
        } else {
            mDescribeVisibility.postValue(GONE);
        }
    }

    private void toggleCustomEditableViewModel(final boolean show) {
        if (mCustomOptionViewModel != null) {
            if (show) {
                mCustomOptionViewModel.setVisibility(true);
            } else {
                mCustomOptionViewModel.setVisibility(false);
            }
            if (mCustomOptionViewModelManual != null)
                mCustomOptionViewModelManual.setVisibility(false);
        }
    }

    private void enableCustomEditableViewModel(final boolean enable) {
                if (mCustomOptionViewModel != null) {
                    if(enable) {
                        mCustomOptionViewModel.setVisibility(false);
                        if (mCustomOptionViewModelManual != null)
                            mCustomOptionViewModelManual.setVisibility(true);
                    } else {
                        mCustomOptionViewModel.setDisabled(true);
                        mCustomOptionViewModel.setTextColor(mApplication.getColor(R.color.grey_hint));
                    }
                }
    }


    @NonNull
    @Override
    public LiveData<AdapterView.OnItemSelectedListener> getItemSelectedListener() {
        return mItemSelectedListener;
    }

    @NonNull
    @Override
    public LiveData<CharSequence[]> getEntries() {
        return mEntries;
    }

    @NonNull
    public String getSelectedEntry() {
        CharSequence[] value = mEntries.getValue();
        Integer index = mSelectedIndex.getValue();
        if(value != null && index != null && value.length > index && index > 0) {
            return value[index].toString();
        }
        return "";
    }

    @Override
    public LiveData<String> getManualText() {
        return mManualText;
    }

    @Override
    public void postEntries(CharSequence[] entries) {
        mEntries.postValue(entries);
    }

    @NonNull
    @Override
    public LiveData<String> getPrompt() {
        return mPrompt;
    }

    @Override
    public void setPrompt(String prompt) {
        mPrompt.postValue(prompt);
    }

    @NonNull
    @Override
    public LiveData<String> getHint() {
        return mHint;
    }


    @NonNull
    @Override
    public LiveData<Integer> getSelectedIndex() {
        return mSelectedIndex;
    }

    @NonNull
    @Override
    public LiveData<Integer> getDescribeVisibility() {
        return mDescribeVisibility;
    }

    @Nullable
    @Override
    public EditableViewModel getCustomOptionViewModel() {
        return mCustomOptionViewModel;
    }

    @Nullable
    @Override
    public EditableViewModel getCustomOptionViewModelManual() {
        return  mCustomOptionViewModelManual;
    }

    @NonNull
    @Override
    public MutableLiveData<Integer> getPosition() {
        return mPosition;
    }

    @Override
    public void postIndex(final int index) {
        new Handler().postDelayed(() -> {
            AdapterView.OnItemSelectedListener listener =
                    Preconditions.verifyNonNull(mItemSelectedListener.getValue(), "ItemSelectedListener");
            listener.onItemSelected(null, null, index, 0);
        }, 0);
    }

    @Override
    public void hideDescribeView() {
        mDescribeVisibility.postValue(GONE);
    }

    public void setType(@NonNull final SpinnerType type) {
        mSpinnerType = Preconditions.requiresNonNull(type, "SpinnerType");
        addTransformations();
    }

    private void addTransformations() {
        if (mCustomOptionViewModelManual != null) {
            mCustomOptionViewModelManual.setVisibility(true);
            TransformationsUtils.addNullableMapSource(mManualText, mCustomOptionViewModelManual.getText(), text -> text);
        }
    }
    public static class Factory extends MVVMViewModel.Factory<SpinnerViewModel> {

        @NonNull
        private final SpinnerProps mSpinnerProps;

        public Factory(@NonNull final Application application,
                       @NonNull final SpinnerProps spinnerProps) {
            super(SpinnerViewModel.class, application);
            mSpinnerProps = Preconditions.requiresNonNull(spinnerProps, "spinnerProps");
        }

        @NonNull
        @Override
        public SpinnerViewModel create() {
            return new SpinnerViewModel(mApplication, mSpinnerProps);
        }
    }
}
