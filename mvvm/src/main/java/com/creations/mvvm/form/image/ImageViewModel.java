package com.creations.mvvm.form.image;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.form.FormViewModelBase;
import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.ImageData;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * This ViewModel works with a Button and is to be used for creating forms.
 */
public class ImageViewModel extends FormViewModelBase implements ImageContract.ViewModel {

    @NonNull
    private MutableLiveData<String> mMessage = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Integer> mTextColor = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Drawable> mBackground = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Integer> mVisibility = new MutableLiveData<>();

    @NonNull
    private final MutableLiveData<String> mImageUrl = new MutableLiveData<>();

    @NonNull
    private final MutableLiveData<String> mTitle = new MutableLiveData<>();

    @NonNull
    private final MutableLiveData<String> mDescription = new MutableLiveData<>();

    @NonNull
    private final LiveRunnable.Mutable mClickEvent = new LiveRunnable.Mutable();

    @NonNull
    private final MutableLiveData<View.OnClickListener> mClickListener = new MutableLiveData<>();


    public ImageViewModel(@NonNull final Application application,
                          @NonNull final ImageData imageData) {
        super(application, "Button items");
        Preconditions.requiresNonNull(imageData, "ImageData");
        setData(imageData);
    }

    @Override
    public void setData(@NonNull ImageData imageData) {
        mImageUrl.setValue(imageData.getUrl());
        mTitle.setValue(imageData.getTitle());
        mDescription.setValue(imageData.getDescription());
        setBackground(getApplication().getDrawable(R.drawable.image_background));
    }

    @Override
    public void changeState() {
        setBackground(getApplication().getDrawable(R.drawable.image_background_yellow));
    }

    @Override
    public void setPosition(final int position) {

    }

    @NonNull
    @Override
    public LiveData<String> getMessage() {
        return mMessage;
    }

    @Override
    public void setMessage(final String message) {
        mMessage.postValue(message);
    }

    @NonNull
    @Override
    public MutableLiveData<String> getImageUrl() {
        return mImageUrl;
    }

    @NonNull
    @Override
    public MutableLiveData<String> getTitle() {
        return mTitle;
    }

    @NonNull
    @Override
    public MutableLiveData<String> getDescription() {
        return mDescription;
    }

    @NonNull
    @Override
    public LiveRunnable.Mutable getClickedEvent() {
        return mClickEvent;
    }

    @Override
    public void onItemClick() {
        mClickEvent.postEvent();
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
    public LiveData<Integer> getVisibility() {
        return mVisibility;
    }

    @Override
    public void setVisibility(Integer visibility) {
        mVisibility.postValue(visibility);
    }

    @Override
    public void setBackground(Drawable drawable) {
        mBackground.postValue(drawable);
    }

    public static class Factory extends MVVMViewModel.Factory<ImageViewModel> {

        @NonNull
        private final ImageData mImageData;

        public Factory(@NonNull final Application application,
                       @NonNull final ImageData imageData) {
            super(ImageViewModel.class, application);
            mImageData = Preconditions.requiresNonNull(imageData, "imageData");
        }

        @NonNull
        @Override
        public ImageViewModel create() {
            return new ImageViewModel(mApplication, mImageData);
        }
    }
}
