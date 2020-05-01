package com.creations.mvvm.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Recycler extends RecyclerView {
    public Recycler(@NonNull Context context) {
        super(context);
    }

    @Override
    public void scrollToPosition(int position) {
        super.scrollToPosition(position);

    }
}
