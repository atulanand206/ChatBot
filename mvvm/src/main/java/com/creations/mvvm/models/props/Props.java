package com.creations.mvvm.models.props;

import java.io.Serializable;

public class Props implements Serializable {

    private boolean mClickable = true;

    public boolean isClickable() {
        return mClickable;
    }

    public void setClickable(boolean mClickable) {
        this.mClickable = mClickable;
    }

}
