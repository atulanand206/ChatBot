package com.creations.mvvm.live;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

/**
 * A {@link LiveTextField} extended to support {@link com.google.android.material.textfield.TextInputEditText}
 * specific fields like error.
 * The same disclaimer applies, use a Mutable or Mediator for modifying values
 * like you would a {@link MutableLiveData}.
 */
@SuppressWarnings("unused")
public class LiveEditable extends LiveTextField {

    @NonNull
    protected final LockingMediatorLiveData<String> mError = new LockingMediatorLiveData<>(null);
    @SuppressWarnings("Convert2MethodRef")
    @NonNull
    protected final LiveData<Boolean> mHasError = Transformations.map(mError, error -> error != null);

    @NonNull
    public LiveData<String> getError() {
        return mError;
    }
    @NonNull
    public LiveData<Boolean> getHasError() {
        return mHasError;
    }

    LiveEditable() {
        this(null);
    }

    LiveEditable(@Nullable final String text) {
        this(text, View.VISIBLE);
    }

    LiveEditable(@Nullable final String text, final int visibility) {
        this(text, visibility, null);
    }

    LiveEditable(@Nullable final String text, final int visibility, @Nullable final String error) {
        super(text, visibility);
        mError.setValue(null);
    }

    protected void setError(@Nullable final String error) {
        mError.postValue(error);
    }

    protected void clearError() {
        setError(null);
    }

    /**
     * The {@link MutableLiveData} equivalent for {@link LiveTextField}. Exposing the
     * ability to change values.
     */
    public static class Mutable extends LiveEditable {

        public Mutable() {
            super();
        }

        public Mutable(@Nullable final String initialText) {
            super(initialText);
        }

        public Mutable(@Nullable final String initialText, final int initialVisibility) {
            super(initialText, initialVisibility);
        }

        public Mutable(@Nullable final String initialText, final int initialVisibility,
                       @Nullable final String error) {
            super(initialText, initialVisibility, error);
        }

        @NonNull
        @Override
        public MutableLiveData<String> getText() {
            return mText;
        }
        @NonNull
        @Override
        public MutableLiveData<String> getError() {
            return mError;
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
        public void setError(@Nullable final String error) {
            super.setError(error);
        }

        @Override
        public void clearError() {
            super.clearError();
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
     * The {@link MediatorLiveData} equivalent for {@link LiveEditable}. Exposing the
     * ability to change values and add sources.
     */
    public static class Mediator extends LiveEditable.Mutable {

        public Mediator() {
            super();
        }

        public Mediator(@Nullable final String initialText) {
            super(initialText);
        }

        public Mediator(@Nullable final String initialText, final int initialVisibility) {
            super(initialText, initialVisibility);
        }

        public Mediator(@Nullable final String initialText, final int initialVisibility,
                        @Nullable final String error) {
            super(initialText, initialVisibility, error);
        }

        @NonNull
        @Override
        public MediatorLiveData<String> getText() {
            return mText;
        }
        @NonNull
        @Override
        public MediatorLiveData<String> getError() {
            return mError;
        }
        @NonNull
        @Override
        public MediatorLiveData<Integer> getVisibility() {
            return mVisibility;
        }

    }

    /**
     * The {@link LockingMediatorLiveData} equivalent for {@link LiveEditable}. Exposing the
     * ability to change values, add sources, and lock sources.
     */
    public static class LockingMediator extends Mediator {

        public LockingMediator() {
            super();
        }

        public LockingMediator(@Nullable final String initialText) {
            super(initialText);
        }

        public LockingMediator(@Nullable final String initialText, final int initialVisibility) {
            super(initialText, initialVisibility);
        }

        public LockingMediator(@Nullable final String initialText, final int initialVisibility,
                        @Nullable final String error) {
            super(initialText, initialVisibility, error);
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
        @NonNull
        @Override
        public LockingMediatorLiveData<String> getError() {
            return mError;
        }

    }

}
