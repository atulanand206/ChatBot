package com.creations.mvvm.live;

import com.creations.condition.Preconditions;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;

/**
 * A {@link LiveEvent} that does not pass data, it just notifies observers.
 */
public class LiveRunnable extends LiveEvent<LiveRunnable.Sentinel> {

    public static class Sentinel { }

    protected void postEvent() {
        postEvent(new Sentinel());
    }

    public void listen(@NonNull final LifecycleOwner owner,
                       @NonNull final Runnable listener) {
        Preconditions.requiresNonNull(listener, "Listener");

        addListener(owner, t -> {
            if (t != null) {
                listener.run();
                handled();
            }
        });
    }
    /**
     * A {@link LiveRunnable} that exposes methods to post events and add sources.
     *
     * The same warnings from {@link MutableLiveData} apply here as well. You will
     * normally not want to expose a Mutable outside your class.
     *
     * @param <T> The data sent in the events.
     */
    public static class Mutable extends LiveRunnable {

        @Override
        public void postEvent() {
            super.postEvent();
        }

        @MainThread
        public  <S> void addSource(@NonNull final LiveRunnable source) {
            super.addSource(source);
        }

        @MainThread
        public  <S> void removeSource(@NonNull final LiveRunnable source) {
            super.removeSource(source);
        }

        /**
         * Similar to {@link LockingMediatorLiveData#lock()} but cannot be reversed.
         */
        @Override
        @SuppressWarnings("unused")
        public void clearSources() {
            super.clearSources();
        }

    }

}
