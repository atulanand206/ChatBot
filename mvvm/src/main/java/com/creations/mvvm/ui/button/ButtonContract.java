package com.creations.mvvm.ui.button;

import android.graphics.drawable.Drawable;
import android.view.View;

import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.viewmodel.IMVVMViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public interface ButtonContract {

    interface ViewModel extends IMVVMViewModel {

        /**
         * @return liveData corresponding to the prompt text.
         */
        @NonNull
        LiveData<String> getMessage();

        void setMessage(final String message);



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



        void setBackground(final Drawable drawable);



    }

}
