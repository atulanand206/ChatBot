package com.creations.mvvm.ui.recycler;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

public class VerticalRecyclerAdapter<T extends IRecyclerViewModel, E extends ViewDataBinding> extends RecyclerAdapter<T, E> {


    public VerticalRecyclerAdapter(@NonNull RecyclerListener<T> listener, int layoutResId) {
        super(listener, layoutResId);
    }
}
