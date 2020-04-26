package com.creations.mvvm.viewmodel;

import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MutableLiveData;

import androidx.annotation.NonNull;

public interface IAnimatorViewModel extends IMVVMViewModel {
    @NonNull
    MutableLiveData<Integer> getContainerId();

    @NonNull
    MutableLiveData<Integer> getLayoutId();

    @NonNull
    MutableLiveData<Integer> getContentId();

    @NonNull
    LiveRunnable getAnimation();

    void animate(boolean show);
}
