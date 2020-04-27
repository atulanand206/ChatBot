package com.creations.mvvm.ui.recycler;

import android.app.Application;

import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public class RecyclerViewModel<T extends Props> extends MenuViewModel<T> implements IRecyclerViewModel<T> {

    @NonNull
    private MutableLiveData<Integer> mSize = new MutableLiveData<>(-1);

    @NonNull
    private MutableLiveData<Integer> mPosition = new MutableLiveData<>(0);

    @NonNull
    private final LiveRunnable.Mutable mClickEvent = new LiveRunnable.Mutable();

    protected RecyclerViewModel(@NonNull Application application,
                                @NonNull final T props) {
        super(application, props);
    }

    @NonNull
    @Override
    public LiveRunnable.Mutable getClickedEvent() {
        return mClickEvent;
    }


    @Override
    public void onRecyclerItemClick() {
        mClickEvent.postEvent();
    }

    @Override
    public void setPosition(final int position) {
        mPosition.postValue(position);
    }

    @NonNull
    @Override
    public LiveData<Integer> getPosition() {
        return mPosition;
    }

    @Override
    public void setSize(@NonNull Integer size) {
        mSize.postValue(size);
    }

    @NonNull
    @Override
    public LiveData<Integer> getSize() {
        return mSize;
    }
}