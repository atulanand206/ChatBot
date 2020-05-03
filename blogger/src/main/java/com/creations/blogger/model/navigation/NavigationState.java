package com.creations.blogger.model.navigation;

import android.content.Context;
import android.graphics.drawable.Drawable;

import com.creations.condition.Preconditions;
import com.creations.mvvm.R;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

public enum NavigationState {

    NOT_YET_OPENED(0, R.drawable.ic_navigation_not_started),
    CURRENT(1, R.drawable.ic_navigation_current),
    SKIPPED(2, R.drawable.ic_navigation_skip),
    COMPLETED(3, R.drawable.ic_navigation_complete);

    private final int mIndex;
    @DrawableRes
    private final int mDrawableResId;

    NavigationState(int index, int drawableResId) {
        this.mIndex = index;
        this.mDrawableResId = drawableResId;
    }

    public int getIndex() {
        return mIndex;
    }

    @SuppressWarnings("unused")
    @DrawableRes
    public int getDrawableResource() {
        return mDrawableResId;
    }

    public Drawable getDrawable(@NonNull final Context context) {
        Preconditions.requiresNonNull(context, "Context");

        return context.getDrawable(mDrawableResId);
    }
}
