package com.creations.blocks.ui.cell;

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

        @NonNull
        LiveRunnable.Mutable getSelectionEvent();

        void setColorResId(@ColorRes final int colorResId);

        void setCharacter(final char character);

        LiveData<Integer> getCellColor();

        void setCellColor(int color);

        LiveData<Integer> getColorResId();

        LiveData<String> getCharacter();

        LiveData<Float> getSide();
    }

}
