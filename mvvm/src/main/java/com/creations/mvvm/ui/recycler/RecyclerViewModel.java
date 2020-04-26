package com.creations.mvvm.ui.recycler;

import android.app.Application;

import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;

public class RecyclerViewModel extends MVVMViewModel implements IRecyclerViewModel {

    @NonNull
    private final LiveRunnable.Mutable mClickEvent = new LiveRunnable.Mutable();

    protected RecyclerViewModel(@NonNull Application application) {
        super(application);
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

    }

}
