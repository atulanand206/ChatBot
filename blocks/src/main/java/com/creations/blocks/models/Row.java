package com.creations.blocks.models;

import com.creations.mvvm.models.props.RecyclerProps;
import com.example.application.utils.RecyclerUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class Row extends RecyclerProps {

    private boolean addVisibility = false;

    private RecyclerUtils.LayoutType layoutType = RecyclerUtils.LayoutType.LINEAR_HORIZONTAL;

    private List<Cell> cells;

    public Row() {
        this(false, RecyclerUtils.LayoutType.LINEAR_HORIZONTAL, new ArrayList<>());
    }

    public Row(@NonNull final List<Cell> cells, RecyclerUtils.LayoutType layoutType) {
        this(false, layoutType, cells);
    }

    public Row(boolean addVisibility, RecyclerUtils.LayoutType layoutType, List<Cell> cells) {
        this.addVisibility = addVisibility;
        this.layoutType = layoutType;
        this.cells = cells;
    }

    public List<Cell> getCells() {
        return cells;
    }

    public String getWord() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Cell cell : cells)
            stringBuilder.append(cell.getCharacter());
        return stringBuilder.toString();
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

    @Override
    public void setColorResId(int colorResId) {
        super.setColorResId(colorResId);
        for (Cell cell : cells)
            cell.setColorResId(colorResId);
    }

    @Override
    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        for (Cell cell : cells)
            cell.setClickable(clickable);
    }

    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
    }
}
