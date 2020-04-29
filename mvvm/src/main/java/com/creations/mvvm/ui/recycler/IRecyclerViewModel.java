package com.creations.mvvm.ui.recycler;

import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuContract;
import com.example.application.utils.RecyclerUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public interface IRecyclerViewModel<T extends Props>  extends MenuContract.ViewModel<T> {

    void setPosition(final int position);

    @NonNull
    RecyclerUtils.LayoutType getLayoutType();

    void setLayoutType(@NonNull RecyclerUtils.LayoutType layoutType);

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
