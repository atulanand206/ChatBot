package com.example.application.utils;

import com.bekawestberg.loopinglayout.library.LoopingLayoutManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerUtils {

    public enum LayoutType {
        GRID, LOOP_HORIZONTAL, LOOP_VERTICAL, LINEAR_HORIZONTAL, LINEAR_VERTICAL
    }

     public static GridLayoutManager grid(@NonNull final RecyclerView recyclerView) {
        return new GridLayoutManager(recyclerView.getContext(), 2);
    }

    public static LoopingLayoutManager loopingHorizontal(@NonNull final RecyclerView recyclerView) {
        return new LoopingLayoutManager(recyclerView.getContext(), LoopingLayoutManager.HORIZONTAL, false);
    }

    public static LoopingLayoutManager loopingVertical(@NonNull final RecyclerView recyclerView) {
        return new LoopingLayoutManager(recyclerView.getContext(), LoopingLayoutManager.VERTICAL, false);
    }

    public static LinearLayoutManager linearHorizontal(@NonNull final RecyclerView recyclerView) {
        return new LinearLayoutManager(recyclerView.getContext(), RecyclerView.HORIZONTAL, false);
    }

    public static LinearLayoutManager linearVertical(@NonNull final RecyclerView recyclerView) {
        return new LinearLayoutManager(recyclerView.getContext(), RecyclerView.VERTICAL, false);
    }
}
