package com.creations.mvvm.models.props;

import com.creations.mvvm.R;

import java.io.Serializable;

import androidx.annotation.ColorRes;

public class Props implements Serializable {

    @ColorRes
    private int colorResId = R.color.pal_pink;

    private boolean mClickable = true;

    public Props() {
    }

    public Props(int colorResId) {
        this.colorResId = colorResId;
    }

    public boolean isClickable() {
        return mClickable;
    }

    public void setClickable(boolean mClickable) {
        this.mClickable = mClickable;
    }

    public int getColorResId() {
        return colorResId;
    }

    public void setColorResId(@ColorRes int colorResId) {
        this.colorResId = colorResId;
    }

    public void setProps(Props cell) {
        setColorResId(cell.getColorResId());
    }
}
