package com.creations.mvvm.models.blocks;

import com.creations.mvvm.R;

import java.io.Serializable;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;

public class Cell implements Serializable {

    @ColorRes
    private int colorResId;

    private char character;

    @ColorRes
    private int textColorResId;

    @DimenRes
    private int textSize;

    @DimenRes
    private int side;

    public Cell(char character) {
        this(R.color.colorPrimary, character, R.color.white, R.dimen.font_x_large, R.dimen.margin_xxxxx_large);
    }

    public Cell(int colorResId, char character, int textColorResId, int textSize, int side) {
        this.colorResId = colorResId;
        this.character = character;
        this.textColorResId = textColorResId;
        this.textSize = textSize;
        this.side = side;
    }

    public int getColorResId() {
        return colorResId;
    }

    public char getCharacter() {
        return character;
    }

    public int getTextColorResId() {
        return textColorResId;
    }

    public int getTextSize() {
        return textSize;
    }

    public int getSide() {
        return side;
    }
}
