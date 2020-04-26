package com.creations.mvvm.ui.animate;

import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.viewmodel.IMVVMViewModel;

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
