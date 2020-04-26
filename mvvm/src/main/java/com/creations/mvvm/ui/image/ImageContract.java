package com.creations.mvvm.ui.image;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.ImageData;
import com.creations.mvvm.ui.IFormViewModelBase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public interface ImageContract {

    interface ViewModel extends IFormViewModelBase {

        void setPosition(int position);

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

        /**
         * @return liveRunnable when the readiness checklist save button is clicked.
         */
        @NonNull
        LiveRunnable.Mutable getClickedEvent();

        /**
         * Called when the Button item is tapped.
         */
        void onItemClick();

        @NonNull
        MutableLiveData<View.OnClickListener> getClickListener();

        void setClickListener(@NonNull View.OnClickListener clickListener);

        void setTextColor(final int colorResId);

        LiveData<Integer> getTextColor();

        LiveData<Drawable> getBackground();

//        LiveData<Integer> getVisibility();
        void setVisibility(final Integer visibility);


        void setBackground(final Drawable drawable);


        void setData(@NonNull final ImageData imageData);

        void changeState();
    }

}
