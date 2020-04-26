package com.creations.mvvm.ui.navigation;

import android.graphics.drawable.Drawable;

import com.creations.mvvm.live.LiveEvent;
import com.creations.mvvm.models.navigation.NavigationBarProps;
import com.creations.mvvm.ui.IFormViewModelBase;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

public interface NavigationBarContract {

    interface ViewModel extends IFormViewModelBase {

        @NonNull
        LiveEvent.Mutable<Integer> getStatusBarColorEvent();

        /**
         * @return liveData corresponding to the prompt text.
         */
        @NonNull
        String getTitle(final int position);

        Drawable getDrawable(final int position);

        void setProps(@NonNull final NavigationBarProps props);

        void setTopColor(int backgroundColorResId);

        /**
         * @return the recycler adapter being used by the teams list.
         */
        @NonNull
        LiveData<RecyclerView.Adapter> getAdapter();

        Integer getLineVisibility(final int position);

        /**
         * @return the list of certificates to be added on the recycler view.
         */
        @NonNull
        NavigationBarProps getProps();

    }

}