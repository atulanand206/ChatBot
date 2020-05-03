package com.creations.blogger.ui.navigation.item;

import android.graphics.drawable.Drawable;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

import androidx.lifecycle.LiveData;

public interface NavItemContract {

    interface ViewModel<T extends Props> extends IRecyclerViewModel<T> {

        Drawable getDrawable();

        LiveData<Integer> getLineVisibility();
    }

}
