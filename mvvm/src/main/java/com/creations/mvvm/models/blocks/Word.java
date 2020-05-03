package com.creations.mvvm.models.blocks;

import com.creations.mvvm.models.props.RecyclerProps;
import com.example.application.utils.RecyclerUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class Word extends RecyclerProps {

    private boolean addVisibility = false;

    private String word;

    private String meaning;

    private String speech;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getSpeech() {
        return speech;
    }

    public void setSpeech(String speech) {
        this.speech = speech;
    }

    private RecyclerUtils.LayoutType layoutType = RecyclerUtils.LayoutType.LINEAR_HORIZONTAL;

    private List<Cell> cells;

    public Word() {
        this(false, RecyclerUtils.LayoutType.LINEAR_HORIZONTAL, new ArrayList<>());
    }

    public Word(@NonNull final List<Cell> cells, RecyclerUtils.LayoutType layoutType) {
        this(false, layoutType, cells);
    }

    public Word(boolean addVisibility, RecyclerUtils.LayoutType layoutType, List<Cell> cells) {
        this.addVisibility = addVisibility;
        this.layoutType = layoutType;
        this.cells = cells;
    }

    public Word(List<Cell> cells) {
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

    @Override
    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        for (Cell cell : cells)
            cell.setClickable(clickable);
    }
}
