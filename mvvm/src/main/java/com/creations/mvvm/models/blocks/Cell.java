package com.creations.mvvm.models.blocks;

import com.creations.mvvm.R;
import com.creations.mvvm.models.props.Props;

import java.io.Serializable;

import androidx.annotation.ColorRes;
import androidx.annotation.DimenRes;

public class Cell extends Props implements Serializable {

    public static class Type {
        public static final int ADD = 0;
        public static final int MINUS = 2;
        public static final int FULL = 1;
    }

    public static class State {
        public static final int SELECTED = 1;
        public static final int NOT_SELECTED = 0;
        public static final int COLORS = 2;
    }

    @ColorRes
    private int colorResId;

    private char character;

    @ColorRes
    private int textColorResId;

    @DimenRes
    private int textSize;

    @DimenRes
    private int side;

    private int type;
    private int state;

    public Cell(char character) {
        this(R.color.colorPrimary, character, R.color.white, R.dimen.font_x_large, R.dimen.margin_xxxxx_large);
        this.type = Type.FULL;
    }

    public Cell(final boolean add) {
        this(R.color.pal_red, '+', R.color.white, R.dimen.font_x_large, R.dimen.margin_xxxxx_large);
        this.type = Type.ADD;
    }

    public Cell(char character, boolean add) {
        this(R.color.pal_red, character, R.color.white, R.dimen.font_x_large, R.dimen.margin_xxxxx_large);
        this.type = add ? Type.ADD : Type.MINUS;
    }

    public Cell(int colorResId, char character, int textColorResId, int textSize, int side) {
        this.colorResId = colorResId;
        this.character = character;
        this.textColorResId = textColorResId;
        this.textSize = textSize;
        this.side = side;
        this.state = State.COLORS;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
