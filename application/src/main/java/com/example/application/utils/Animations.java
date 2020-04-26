package com.example.application.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.View;

import androidx.annotation.Nullable;

public class Animations {

    public void crossfade(@Nullable final Context context, @Nullable final View rootView) {
        if (context == null || rootView == null)
            return;

        View containerView = rootView.findViewById(com.example.application.R.id.animate_container);

        if (containerView == null)
            return;

        View contentView = containerView.findViewById(com.example.application.R.id.animate_after);
        final View loadingView = containerView.findViewById(com.example.application.R.id.animate_before);

        if (contentView == null || loadingView == null)
            return;

        // Set the content view to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        contentView.setAlpha(0f);
        contentView.setVisibility(View.VISIBLE);

        int shortAnimationDuration = context.getResources().getInteger(android.R.integer.config_shortAnimTime);

        // Animate the content view to 100% opacity, and clear any animation
        // listener set on the view.
        contentView.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);

        // Animate the loading view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        loadingView.animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        loadingView.setVisibility(View.GONE);
                    }
                });
    }
}
