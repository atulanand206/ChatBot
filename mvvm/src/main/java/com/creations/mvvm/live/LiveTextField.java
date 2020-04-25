package com.creations.mvvm.live;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

/**
 * Contains the {@link LiveData} types usually desired for a {@link android.widget.TextView}
 * as well as some helper functions to manage it.
 * You need to use a Mutable or Mediator to modify it like you would a {@link MutableLiveData}.
 */
public class LiveTextField {

    @NonNull
    protected final LockingMediatorLiveData<String> mText;
    @NonNull
    protected final LockingMediatorLiveData<Integer> mVisibility;

    public LiveTextField() {
        this(null);
    }

    public LiveTextField(@Nullable final String initialText) {
        this(initialText, View.VISIBLE);
    }

    public LiveTextField(final int initialVisibility) {
        this(null, initialVisibility);
    }

    public LiveTextField(@Nullable final String initialText, final int initialVisibility) {
        mText = new LockingMediatorLiveData<>(initialText);
        mVisibility = new LockingMediatorLiveData<>(initialVisibility);
    }

    @NonNull
    public LiveData<String> getText() {
        return mText;
    }
    @NonNull
    public LiveData<Integer> getVisibility() {
        return mVisibility;
    }

    protected void setText(@Nullable final String text) {
        mText.postValue(text);
    }

    protected void hide() {
        mVisibility.postValue(View.GONE);
    }

    protected void unhide() {
        mVisibility.postValue(View.VISIBLE);
    }

    protected void toggleVisible(final boolean show) {
        if (show) {
            unhide();
        } else {
            hide();
        }
    }

    /**
     * The {@link MutableLiveData} equivalent for {@link LiveTextField}. Exposing the
     * ability to change values.
     */
    public static class Mutable extends LiveTextField {

        public Mutable() {
            super();
        }

        public Mutable(@Nullable final String initialText) {
            super(initialText);
        }

        public Mutable(final int initialVisibility) {
            super(initialVisibility);
        }

        public Mutable(@Nullable final String initialText, final int initialVisibility) {
            super(initialText, initialVisibility);
        }

        @NonNull
        @Override
        public MutableLiveData<String> getText() {
            return mText;
        }
        @NonNull
        @Override
        public MutableLiveData<Integer> getVisibility() {
            return mVisibility;
        }

        @Override
        public void setText(@Nullable final String text) {
            super.setText(text);
        }

        @Override
        public void hide() {
           super.hide();
        }

        @Override
        public void unhide() {
            super.unhide();
        }

        @Override
        public void toggleVisible(final boolean show) {
            super.toggleVisible(show);
        }
    }

    /**
     * The {@link MediatorLiveData} equivalent for {@link LiveTextField}. Exposing the
     * ability to change values and add sources.
     */
    public static class Mediator extends Mutable {

        public Mediator() {
            super();
        }

        public Mediator(@Nullable final String initialText) {
            super(initialText);
        }

        public Mediator(final int initialVisibility) {
            super(initialVisibility);
        }

        public Mediator(@Nullable final String initialText, int initialVisibility) {
            super(initialText, initialVisibility);
        }

        @NonNull
        @Override
        public MediatorLiveData<String> getText() {
            return mText;
        }
        @NonNull
        @Override
        public MediatorLiveData<Integer> getVisibility() {
            return mVisibility;
        }

    }

    /**
     * The {@link LockingMediatorLiveData} equivalent for {@link LiveTextField}. Exposing the
     * ability to change values, add sources, and lock sources.
     */
    @SuppressWarnings("unused")
    public static class LockingMediator extends Mediator {

        public LockingMediator() {
            super();
        }

        public LockingMediator(@Nullable final String initialText) {
            super(initialText);
        }

        public LockingMediator(final int initialVisibility) {
            super(initialVisibility);
        }

        public LockingMediator(@Nullable final String initialText, int initialVisibility) {
            super(initialText, initialVisibility);
        }

        @NonNull
        @Override
        public LockingMediatorLiveData<String> getText() {
            return mText;
        }
        @NonNull
        @Override
        public LockingMediatorLiveData<Integer> getVisibility() {
            return mVisibility;
        }

    }

}
