package com.creations.mvvm.ui.menu;

import com.creations.mvvm.live.LiveEvent;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.text.TextContract;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public interface MenuContract {

    interface ViewModel<T extends Props> extends TextContract.ViewModel<T> {

        LiveData<Integer> getProgressBarVisibility();

        void setVisibility(final Integer visibility);

        LiveData<Integer> getVisibility();

        @NonNull
        LiveEvent.Mutable<Integer> getStatusBarColorEvent();

        void setTopColor(int backgroundColorResId);

        LiveData<Integer> getBackgroundColor();

        void setBackgroundColor(@ColorRes int backgroundColorResId);

        void setProgressBarVisibility(int progressBarVisibility);

    }

}
