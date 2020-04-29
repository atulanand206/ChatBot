package com.creations.mvvm.models.blocks;

import com.creations.mvvm.models.props.Props;
import com.example.application.utils.RecyclerUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class Row extends Props {

    private boolean addVisibility = false;

    private RecyclerUtils.LayoutType layoutType = RecyclerUtils.LayoutType.LINEAR_HORIZONTAL;

    private List<Cell> cells;

    public Row() {
        this.cells = new ArrayList<>();
        this.layoutType = RecyclerUtils.LayoutType.LINEAR_HORIZONTAL;
    }

    public Row(@NonNull final Cell cell) {
        this.cells = new ArrayList<>();
        this.cells.add(cell);
    }

    public Row(@NonNull final List<Cell> cells) {
        this.cells = cells;
    }

    public Row(final boolean addVisibility,
               @NonNull final List<Cell> cells) {
        this.addVisibility = addVisibility;
        this.cells = cells;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public boolean isAddVisibility() {
        return addVisibility;
    }

    public void setAddVisibility(final boolean addVisibility) {
        this.addVisibility = addVisibility;
    }

    public RecyclerUtils.LayoutType getLayoutType() {
        return layoutType;
    }

    public void setLayoutType(RecyclerUtils.LayoutType layoutType) {
        this.layoutType = layoutType;
    }
}
