package com.creations.mvvm.ui.blocks;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

import androidx.annotation.ColorInt;
import androidx.annotation.ColorRes;
import androidx.annotation.Dimension;
import androidx.lifecycle.LiveData;

public interface CellContract {

    interface ViewModel<T extends Props> extends IRecyclerViewModel<T> {

        void setColorResId(@ColorRes final int colorResId);

        void setCharacter(final char character);

        void setTextColorResId(@ColorInt int textColorResId);

        void setTextSize(@Dimension final float textSize);

        LiveData<Integer> getColorResId();

        LiveData<String> getCharacter();

        LiveData<Integer> getTextColorResId();

        LiveData<Float> getTextSize();

        LiveData<Float> getSide();
    }

}
