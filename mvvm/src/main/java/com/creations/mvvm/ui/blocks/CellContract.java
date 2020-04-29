package com.creations.mvvm.ui.blocks;

import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public interface CellContract {

    interface ViewModel<T extends Props> extends IRecyclerViewModel<T> {

        @NonNull
        LiveRunnable.Mutable getAddEvent();

        void setColorResId(@ColorRes final int colorResId);

        void setCharacter(final char character);

        LiveData<Integer> getColorResId();

        LiveData<String> getCharacter();

        LiveData<Float> getSide();
    }

}
