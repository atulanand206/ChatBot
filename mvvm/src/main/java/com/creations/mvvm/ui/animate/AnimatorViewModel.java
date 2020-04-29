package com.creations.mvvm.ui.animate;

import android.app.Application;

import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuViewModel;

import androidx.annotation.NonNull;

public abstract class AnimatorViewModel<T extends Props> extends MenuViewModel<T> implements IAnimatorViewModel<T> {

    @NonNull
    private final MutableLiveData<Integer> mContainerId = new MutableLiveData<>(com.example.application.R.id.animate_container);
    @NonNull
    private final MutableLiveData<Integer> mLayoutId = new MutableLiveData<>();
    @NonNull
    private final MutableLiveData<Integer> mContentId = new MutableLiveData<>();
    @NonNull
    private final LiveRunnable.Mutable mAnimation = new LiveRunnable.Mutable();

    protected AnimatorViewModel(@NonNull Application application,
                                @NonNull final T props) {
        super(application, props);
    }

    @NonNull
    @Override
    public MutableLiveData<Integer> getContainerId() {
        return mContainerId;
    }

    @NonNull
    @Override
    public MutableLiveData<Integer> getLayoutId() {
        return mLayoutId;
    }

    @NonNull
    @Override
    public MutableLiveData<Integer> getContentId() {
        return mContentId;
    }

    @NonNull
    @Override
    public LiveRunnable getAnimation() {
        return mAnimation;
    }

    @Override
    public void animate(final boolean show) {
        if (show) {
            mContentId.setValue(com.example.application.R.id.animate_after);
            mLayoutId.setValue(com.example.application.R.id.animate_before);
        } else {
            mContentId.setValue(com.example.application.R.id.animate_before);
            mLayoutId.setValue(com.example.application.R.id.animate_after);
        }
        mAnimation.postEvent();
    }
}
