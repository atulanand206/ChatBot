package com.example.application.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.creations.condition.Preconditions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class DisabledViewPager extends ViewPager {

    private boolean scrollingEnabled = true;

    public DisabledViewPager(@NonNull Context context) {
        super(context);

        Preconditions.requiresNonNull(context, "Context");
    }

    public DisabledViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        Preconditions.requiresNonNull(context, "Context");
    }

    public boolean isScrollingEnabled() {
        return scrollingEnabled;
    }

    public void setScrollingEnabled(boolean scrollingEnabled) {
        this.scrollingEnabled = scrollingEnabled;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return isScrollingEnabled() && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return isScrollingEnabled() && super.onInterceptTouchEvent(event);
    }
}
