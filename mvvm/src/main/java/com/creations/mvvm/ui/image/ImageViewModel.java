package com.creations.mvvm.ui.image;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.creations.blogger.callback.EmptyResponseCallback;
import com.creations.blogger.model.APIResponseBody;
import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.ImageData;
import com.creations.mvvm.ui.FormViewModelBase;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private final MutableLiveData<String> mImageUrl = new MutableLiveData<>();

    @NonNull
    private final MutableLiveData<String> mTitle = new MutableLiveData<>();

    @NonNull
    private final MutableLiveData<String> mDescription = new MutableLiveData<>();

    @NonNull
    private final LiveRunnable.Mutable mClickEvent = new LiveRunnable.Mutable();

    @NonNull
    private final MutableLiveData<View.OnClickListener> mClickListener = new MutableLiveData<>();

    @NonNull
    private final MutableLiveData<ImageData> mImageData = new MutableLiveData<>();

    @NonNull
    private final MutableLiveData<Integer> mProgressBarVisibility = new MutableLiveData<>(View.GONE);


    public ImageViewModel(@NonNull final Application application,
                          @NonNull final ImageData imageData) {
        super(application, "Button items");
        Preconditions.requiresNonNull(imageData, "ImageData");
//        mProgressBarVisibility.setValue(View.VISIBLE);
//        mProgressBarVisibility.setValue(View.GONE);
        setVisibility(View.VISIBLE);
        setData(imageData);
    }

    @Override
    public void setData(@NonNull ImageData imageData) {
        String url = imageData.getUrl();
        url = "https://www.gstatic.com/webp/gallery/5.jpg";
        imageData.setUrl(url);
//        mProgressBarVisibility.setValue(View.VISIBLE);
        imageData.setImageLoadCallback(new EmptyResponseCallback() {
            @Override
            public void onSuccess() {
                String url1 = imageData.getUrl();
            }

            @Override
            public void onError(int statusCode, @NonNull String errorResponse, @NonNull APIResponseBody serializedErrorResponse, @Nullable Exception e) {

            }
        });
        mImageUrl.setValue(url);
        mTitle.setValue(imageData.getTitle());
        mDescription.setValue(imageData.getDescription());
        mImageData.postValue(imageData);
        setBackground(getApplication().getDrawable(R.drawable.image_background));
    }

    @Override
    public void changeState() {
        setBackground(getApplication().getDrawable(R.drawable.image_background_yellow));
        setOverlayVisibility();
    }

    private void setOverlayVisibility() {

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
    public LiveData<ImageData> getImageData() {
        return mImageData;
    }

    @NonNull
    @Override
    public MutableLiveData<Integer> getProgressBarVisibility() {
        return mProgressBarVisibility;
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