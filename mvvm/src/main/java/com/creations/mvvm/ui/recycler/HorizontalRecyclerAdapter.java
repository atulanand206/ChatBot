package com.creations.mvvm.ui.recycler;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

public class HorizontalRecyclerAdapter<T extends IRecyclerViewModel, E extends ViewDataBinding> extends RecyclerAdapter<T, E> {


    public HorizontalRecyclerAdapter(@NonNull RecyclerListener<T> listener, int layoutResId) {
        super(listener, layoutResId);
    }
}
