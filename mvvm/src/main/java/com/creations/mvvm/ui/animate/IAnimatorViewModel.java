package com.creations.mvvm.ui.animate;

import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public interface IAnimatorViewModel<T extends Props> extends MenuContract.ViewModel<T> {
    @NonNull
    LiveData<Integer> getContainerId();

    @NonNull
    LiveData<Integer> getLayoutId();

    @NonNull
    LiveData<Integer> getContentId();

    @NonNull
    LiveRunnable getAnimation();

    void animate(boolean show);
}
