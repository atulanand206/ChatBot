package com.creations.mvvm.ui.recycler;

import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.viewmodel.IMVVMViewModel;

import androidx.annotation.NonNull;

public interface IRecyclerViewModel extends IMVVMViewModel {

    void setPosition(int position);
    /**
     * @return liveRunnable when the readiness checklist save button is clicked.
     */
    @NonNull
    LiveRunnable.Mutable getClickedEvent();

    /**
     * Called when the Button item is tapped.
     */
    void onRecyclerItemClick();

}
