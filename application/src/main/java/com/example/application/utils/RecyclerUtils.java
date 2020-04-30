package com.example.application.utils;

import android.content.Context;

import com.bekawestberg.loopinglayout.library.LoopingLayoutManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerUtils {

    public enum LayoutType {
        GRID, LOOP_HORIZONTAL, LOOP_VERTICAL, LINEAR_HORIZONTAL, LINEAR_VERTICAL
    }

     public static GridLayoutManager grid(@NonNull final Context context) {
        return new GridLayoutManager(context, 2);
    }

    public static LoopingLayoutManager loopingHorizontal(@NonNull final Context context) {
        return new LoopingLayoutManager(context, LoopingLayoutManager.HORIZONTAL, false);
    }

    public static LoopingLayoutManager loopingVertical(@NonNull final Context context) {
        return new LoopingLayoutManager(context, LoopingLayoutManager.VERTICAL, false);
    }

    public static LinearLayoutManager linearHorizontal(@NonNull final Context context) {
        return new LinearLayoutManager(context, RecyclerView.HORIZONTAL, false);
    }

    public static LinearLayoutManager linearVertical(@NonNull final Context context) {
        return new LinearLayoutManager(context, RecyclerView.VERTICAL, false);
    }

    public static RecyclerView.LayoutManager layoutManager(@NonNull final Context context,
                                                           @NonNull final LayoutType type) {
        RecyclerView.LayoutManager layoutManager;
        switch (type) {
            case GRID:
                layoutManager = RecyclerUtils.grid(context);
                break;
            case LOOP_VERTICAL:
                layoutManager = RecyclerUtils.loopingVertical(context);
                break;
            case LOOP_HORIZONTAL:
                layoutManager = RecyclerUtils.loopingHorizontal(context);
                break;
            case LINEAR_HORIZONTAL:
                layoutManager = RecyclerUtils.linearHorizontal(context);
                break;
            case LINEAR_VERTICAL:
            default:
                layoutManager = RecyclerUtils.linearVertical(context);
                break;
        }
        return layoutManager;
    }
}
