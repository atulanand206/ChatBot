package com.creations.mvvm.ui.image;

import android.graphics.drawable.Drawable;

import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.ImageData;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public interface ImageContract {

    interface ViewModel extends IRecyclerViewModel {

        /**
         * @return liveData corresponding to the prompt text.
         */
        @NonNull
        LiveData<String> getMessage();

        void setMessage(final String message);


        @NonNull
        LiveData<ImageData> getImageData();

        @NonNull
        MutableLiveData<Integer> getProgressBarVisibility();

        @NonNull
        MutableLiveData<String> getImageUrl();

        @NonNull
        MutableLiveData<String> getTitle();

        @NonNull
        MutableLiveData<String> getDescription();

        void setTextColor(final int colorResId);

        LiveData<Integer> getTextColor();

        LiveData<Drawable> getBackground();

        void setBackground(final Drawable drawable);

        void setData(@NonNull final ImageData imageData);
    }

}
