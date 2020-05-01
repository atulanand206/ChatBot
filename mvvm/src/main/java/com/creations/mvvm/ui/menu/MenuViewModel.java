package com.creations.mvvm.ui.menu;

import android.app.Application;
import android.view.View;

import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.live.LiveEvent;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.prop.PropViewModel;
import com.creations.mvvm.ui.text.TextViewModel;
import com.creations.tools.utils.JsonConvertor;
import com.example.application.messages.IMessageManager;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class MenuViewModel<T extends Props> extends TextViewModel<T> implements MenuContract.ViewModel<T> {

    protected IMessageManager mMessageManager;

    protected JsonConvertor jsonConvertor;

    @NonNull
    private final MutableLiveData<Integer> mVisibility = new MutableLiveData<>(View.GONE);

    @NonNull
    private final LiveEvent.Mutable<Integer> mStatusBarColorEvent = new LiveEvent.Mutable<>();

    @NonNull
    private final MutableLiveData<Integer> mProgressBarVisibility = new MutableLiveData<>(View.GONE);

    @NonNull
    private final MutableLiveData<Integer> mBackgroundColor = new MutableLiveData<>(R.color.white);

    public MenuViewModel(@NonNull final Application application,
                         @NonNull final T props) {
        super(application, props);
        setVisibility(View.VISIBLE);
    }

    public MenuViewModel(@NonNull final Application application,
                         @NonNull final T props,
                         final int visibility) {
        super(application, props);
        setVisibility(visibility);
    }

    private void setMessageManager(IMessageManager messageManager) {
        this.mMessageManager = messageManager;
    }

    public void setJsonConvertor(JsonConvertor jsonConvertor) {
        this.jsonConvertor = jsonConvertor;
    }

    @Override
    public LiveData<Integer> getVisibility() {
        return mVisibility;
    }

    @Override
    public void setVisibility(Integer visibility) {
        mVisibility.postValue(visibility);
    }

    @NonNull
    @Override
    public LiveEvent.Mutable<Integer> getStatusBarColorEvent() {
        return mStatusBarColorEvent;
    }

    @Override
    public void setTopColor(@ColorRes final int backgroundColorResId) {
        mStatusBarColorEvent.postEvent(backgroundColorResId);
    }

    @NonNull
    @Override
    public MutableLiveData<Integer> getProgressBarVisibility() {
        return mProgressBarVisibility;
    }

    @Override
    public void setProgressBarVisibility(final int progressBarVisibility) {
        this.mProgressBarVisibility.postValue(progressBarVisibility);
    }

    @Override
    public LiveData<Integer> getBackgroundColor() {
        return mBackgroundColor;
    }

    @Override
    public void setBackgroundColor(@ColorRes final int backgroundColorResId) {
        mBackgroundColor.postValue(backgroundColorResId);
    }

    public static class Factory<T> extends PropViewModel.Factory<Props> {

        @NonNull
        private final Props mProps;

        @NonNull
        private final IMessageManager mMessageManager;

        @NonNull
        private final JsonConvertor mJsonConvertor;

        public Factory(@NonNull final Application application,
                       @NonNull final Props props,
                       @NonNull final IMessageManager messageManager,
                       @NonNull final JsonConvertor jsonConvertor) {
            super(application, props);
            mProps = Preconditions.requiresNonNull(props, "Props");
            mMessageManager = Preconditions.requiresNonNull(messageManager, "MessageManager");
            mJsonConvertor = Preconditions.requiresNonNull(jsonConvertor, "JsonConvertor");
        }

        @NonNull
        @Override
        public MenuViewModel create() {
            MenuViewModel<Props> propsMenuViewModel = new MenuViewModel<>(mApplication, mProps);
            propsMenuViewModel.setMessageManager(mMessageManager);
            propsMenuViewModel.setJsonConvertor(mJsonConvertor);
            return propsMenuViewModel;
        }
    }
}
