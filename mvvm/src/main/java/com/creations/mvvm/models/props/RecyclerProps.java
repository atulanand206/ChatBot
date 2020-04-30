package com.creations.mvvm.models.props;

import java.io.Serializable;

public class RecyclerProps extends Props implements Serializable {

    private int scrolledIndex = 0;

    public int getScrolledIndex() {
        return scrolledIndex;
    }

    public void setScrolledIndex(final int scrolledIndex) {
        this.scrolledIndex = scrolledIndex;
    }

    private int clickedIndex = 0;

    public int getClickedIndex() {
        return clickedIndex;
    }

    public void setClickedIndex(final int clickedIndex) {
        this.clickedIndex = clickedIndex;
    }
}
