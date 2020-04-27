package com.creations.mvvm.ui.button;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.creations.condition.Preconditions;
import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.ButtonProps;
import com.creations.mvvm.ui.menu.MenuViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * This ViewModel works with a Button and is to be used for creating forms.
 */
public class ButtonViewModel extends MenuViewModel<ButtonProps> implements ButtonContract.ViewModel<ButtonProps> {

    @NonNull
    private MutableLiveData<String> mMessage = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Integer> mTextColor = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Drawable> mBackground = new MutableLiveData<>();

    @NonNull
    private final LiveRunnable.Mutable mUpdateEvent = new LiveRunnable.Mutable();

    @NonNull
    private final MutableLiveData<View.OnClickListener> mClickListener = new MutableLiveData<>();


    public ButtonViewModel(@NonNull final Application application,
                           @NonNull final ButtonProps buttonProps) {
        super(application, buttonProps);
        Preconditions.requiresNonNull(buttonProps, "ButtonProps");
        mMessage.postValue(buttonProps.getMessage());
    }

    @NonNull
    @Override
    public LiveData<String> getMessage() {
        return mMessage;
    }

    @NonNull
    @Override
    public void setMessage(final String message) {
        mMessage.postValue(message);
    }

    @NonNull
    @Override
    public LiveRunnable.Mutable getClickedEvent() {
        return mUpdateEvent;
    }

    @Override
    public void onItemClick() {
        mUpdateEvent.postEvent();
    }

    @NonNull
    @Override
    public MutableLiveData<View.OnClickListener> getClickListener() {
        return mClickListener;
    }

    @Override
    public void setClickListener(@NonNull final View.OnClickListener clickListener) {
        mClickListener.postValue(clickListener);
    }

    @Override
    public void setTextColor(int colorResId) {
        mTextColor.postValue(colorResId);
    }

    @NonNull
    @Override
    public LiveData<Integer> getTextColor() {
        return mTextColor;
    }

    @Override
    public LiveData<Drawable> getBackground() {
        return mBackground;
    }

    @Override
    public void setBackground(Drawable drawable) {
        mBackground.postValue(drawable);
    }

    public static class Factory extends MVVMViewModel.Factory<ButtonViewModel> {

        @NonNull
        private final ButtonProps mButtonProps;

        public Factory(@NonNull final Application application,
                       @NonNull final ButtonProps ButtonProps) {
            super(ButtonViewModel.class, application);
            mButtonProps = Preconditions.requiresNonNull(ButtonProps, "ButtonProps");
        }

        @NonNull
        @Override
        public ButtonViewModel create() {
            return new ButtonViewModel(mApplication, mButtonProps);
        }
    }
}
