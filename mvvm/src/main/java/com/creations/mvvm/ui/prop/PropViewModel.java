package com.creations.mvvm.ui.prop;

import android.app.Application;

import com.creations.condition.Preconditions;
import com.creations.mvvm.live.LiveEvent;
import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class PropViewModel<T extends Props> extends MVVMViewModel implements PropContract.ViewModel<T> {

    public PropViewModel(@NonNull final Application application,
                         @NonNull final T props) {
        super(application);
        mProps = props;
    }

    @NonNull
    private final LiveEvent.Mutable<T> mPropsEvent = new LiveEvent.Mutable<>();
    private T mProps;

    @NonNull
    @Override
    public T getProps() {
        return mProps;
    }

    @Override
    public void setProps(@NonNull T props) {
        mProps = props;
    }

    @NonNull
    private MutableLiveData<Integer> mId = new MutableLiveData<>();

    @NonNull
    @Override
    public LiveData<Integer> getId() {
        return mId;
    }

    @Override
    public void setId(@IdRes final int id) {
        mId.postValue(id);
    }

    @NonNull
    @Override
    public LiveEvent.Mutable<T> getPropsEvent() {
        return mPropsEvent;
    }

    @NonNull
    private final LiveRunnable.Mutable mKeyboardCloseEvent = new LiveRunnable.Mutable();

    @NonNull
    private final LiveRunnable.Mutable mHideNavigationEvent = new LiveRunnable.Mutable();
    @NonNull
    @Override
    public LiveRunnable.Mutable getCloseKeyboardEvent() {
        return mKeyboardCloseEvent;
    }

    @Override
    public void closeKeyboard() {
        mKeyboardCloseEvent.postEvent();
    }

    @NonNull
    @Override
    public LiveRunnable.Mutable getHideNavigationEvent() {
        return mHideNavigationEvent;
    }

    @Override
    public void hideNavigation() {
        mHideNavigationEvent.postEvent();
    }

    private final MutableLiveData<Boolean> mClickable = new MutableLiveData<>(true);

    @NonNull
    @Override
    public MutableLiveData<Boolean> isClickable() {
        return mClickable;
    }

    @Override
    public void setClickable(final boolean clickable) {
        mClickable.postValue(clickable);
    }

    public static class Factory<T extends Props> extends MVVMViewModel.Factory<PropViewModel> {

        @NonNull
        private final T mProps;

        public Factory(@NonNull final Application application,
                       @NonNull final T props) {
            super(PropViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
        }

        @NonNull
        @Override
        public PropViewModel create() {
            return new PropViewModel<>(mApplication, mProps);
        }
    }
}
