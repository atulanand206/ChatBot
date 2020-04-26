package com.creations.mvvm.ui.recycler;

import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.viewmodel.IMVVMViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public interface IRecyclerViewModel extends IMVVMViewModel {

    void setPosition(final int position);
    /**
     * @return liveRunnable when the readiness checklist save button is clicked.
     */
    @NonNull
    LiveRunnable.Mutable getClickedEvent();

    /**
     * Called when the Button item is tapped.
     */
    void onRecyclerItemClick();

    @NonNull
    LiveData<Integer> getPosition();

    void setSize(@NonNull Integer size);

    @NonNull
    LiveData<Integer> getSize();
}
