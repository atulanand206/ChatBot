package com.creations.mvvm.ui.edit;

import android.app.Application;

import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.live.LiveEvent;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.prop.PropViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class EditViewModel<T extends Props> extends PropViewModel<T> implements EditContract.ViewModel<T> {

    @NonNull
    private final MutableLiveData<Boolean> mEditable = new MutableLiveData<>(false);

    @NonNull
    private final LiveEvent.Mutable<T> mOnClickEvent = new LiveEvent.Mutable<>();

    @ColorRes
    private int[] palette = new int[] {R.color.pal_blue, R.color.pal_green, R.color.pal_orange,
            R.color.pal_pink, R.color.pal_red, R.color.pal_yellow};
    private int currentPaletteIndex = 0;

    public EditViewModel(@NonNull final Application application,
                         @NonNull final T props) {
        super(application, props);

    }

    @NonNull
    @Override
    public LiveData<Boolean> getEditable() {
        return mEditable;
    }

    @Override
    public void setEditable(final boolean editable) {
        mEditable.postValue(editable);
    }

    @Override
    public void shuffle() {
        currentPaletteIndex = (currentPaletteIndex+1) % palette.length;
    }

    @ColorRes
    @Override
    public int getActiveColor() {
        return palette[currentPaletteIndex];
    }

    @Override
    public void onClick() {
        mOnClickEvent.postEvent(getProps());
    }

    @Override
    public void onClick(@NonNull final Object object) {

    }

    public static class Factory<T extends Props> extends MVVMViewModel.Factory<EditViewModel> {

        @NonNull
        private final T mProps;

        public Factory(@NonNull final Application application,
                       @NonNull final T props) {
            super(EditViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
        }

        @NonNull
        @Override
        public EditViewModel create() {
            return new EditViewModel<>(mApplication, mProps);
        }
    }
}
