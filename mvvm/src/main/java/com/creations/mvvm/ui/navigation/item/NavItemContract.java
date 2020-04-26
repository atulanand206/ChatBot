package com.creations.mvvm.ui.navigation.item;

import android.graphics.drawable.Drawable;

import com.creations.mvvm.models.navigation.NavigationItem;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public interface NavItemContract {

    interface ViewModel extends IRecyclerViewModel {

        @NonNull
        String getTitle();

        Drawable getDrawable();

        LiveData<Integer> getLineVisibility();

        void setData(@NonNull final NavigationItem item);
    }

}
