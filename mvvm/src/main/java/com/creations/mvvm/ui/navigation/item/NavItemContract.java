package com.creations.mvvm.ui.navigation.item;

import android.graphics.drawable.Drawable;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public interface NavItemContract {

    interface ViewModel<T extends Props> extends IRecyclerViewModel<T> {

        @NonNull
        String getTitle();

        Drawable getDrawable();

        LiveData<Integer> getLineVisibility();
    }

}
