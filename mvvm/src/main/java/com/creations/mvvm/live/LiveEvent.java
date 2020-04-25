package com.creations.mvvm.live;

import android.os.Looper;

import com.creations.condition.Preconditions;

import java.util.Map;

import androidx.annotation.CallSuper;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

/**
 * Very similar to a {@link MediatorLiveData<T>}, this is for non-persistent values.
 * It should be used for things like Lifecycle aware callbacks that
 * don't need to persist the data once it is observed once by all observers.
 *
 * This does not directly expose the methods to post an event or add sources.
 * You will need a Mutable instance to do so.
 *
 * @param <T> The type it will dispatch to listeners. The dispatched value will always be NonNull
 */
public class LiveEvent<T> extends LiveData<T> {

    @NonNull
    private final SafeIterableMap<LiveData<?>, Source> mSources = new SafeIterableMap<>();

    public interface NullableListener<T> {

        void accept(@Nullable final T t);

    }

    public interface NonNullListener<T> {

        void accept(@NonNull final T t);

    }

    @SuppressWarnings("unused")
    public static <T> NullableListener<T> toNullableListener(@NonNull final NonNullListener<T> listener) {
        Preconditions.requiresNonNull(listener, "NonNullListenerOfT");
        return t -> {
            if (t != null) {
                listener.accept(t);
            }
        };
    }

    public LiveEvent() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            setValue(null);
        } else {
            postValue(null);
        }
    }

    protected void postEvent(@NonNull final T event) {
        Preconditions.requiresNonNull(event, "EventOfT");
        if (getValue() != null) {
            postValue(null);
        }

        postValue(event);
    }

    public void listen(@NonNull final LifecycleOwner owner,
                       @NonNull final NonNullListener<? super T> listener) {
        Preconditions.requiresNonNull(owner, "LifecycleOwner");
        Preconditions.requiresNonNull(listener, "Listener");

        addListener(owner, t -> {
            if (t != null) {
                listener.accept(t);
                handled();
            }
        });
    }

    @SuppressWarnings("WeakerAccess")
    public void handled() {
        postValue(null);
    }

    @SuppressWarnings("WeakerAccess")
    protected void addListener(@NonNull final LifecycleOwner owner,
                               @NonNull final NullableListener<T> listener) {
        Preconditions.requiresNonNull(owner, "LifecycleOwner");
        Preconditions.requiresNonNull(listener, "NullableListenerOfT");

        listener.accept(getValue());

        observe(owner, listener::accept);
    }

    @MainThread
    protected void addSource(@NonNull final LiveEvent<T> source) {
        Source e = new Source(source);
        Source existing = mSources.putIfAbsent(source, e);
        if (existing == null && hasActiveObservers()) {
            e.plug();
        }
    }

    @MainThread
    protected  <S> void removeSource(@NonNull final LiveData<S> source) {
        Preconditions.requiresNonNull(source, "LiveDataSource");

        Source sourceInternal = mSources.remove(source);
        if (sourceInternal != null) {
            sourceInternal.unplug();
        }
    }

    protected void clearSources() {
        for(Map.Entry<LiveData<?>, Source> source : mSources) {
            removeSource(source.getKey());
        }
    }

    @CallSuper
    @Override
    protected void onActive() {
        for (Map.Entry<LiveData<?>, Source> source : mSources) {
            source.getValue().plug();
        }
    }

    @CallSuper
    @Override
    protected void onInactive() {
        for (Map.Entry<LiveData<?>, Source> source : mSources) {
            source.getValue().unplug();
        }
    }

    public class Source implements Observer<T> {

        @NonNull
        private final LiveEvent<T> mLiveEvent;

        public Source(@NonNull final LiveEvent<T> liveData) {
            mLiveEvent = Preconditions.requiresNonNull(liveData, "Live");
        }

        public void plug() {
            mLiveEvent.observeForever(this);
        }

        public void unplug() {
            mLiveEvent.removeObserver(this);
        }

        @Override
        public void onChanged(@Nullable T t) {
            if (t != null) {
                postEvent(t);
            }
        }
    }

    /**
     * A {@link LiveEvent<T>} that exposes methods to post events and add sources.
     *
     * The same warnings from {@link MutableLiveData} apply here as well. You will
     * normally not want to expose a Mutable outside your class.
     *
     * @param <T> The data sent in the events.
     */
    public static class Mutable<T> extends LiveEvent<T> {

        @Override
        public void postEvent(@NonNull final T event) {
            Preconditions.requiresNonNull(event, "EventOfT");

            super.postEvent(event);
        }

        @MainThread
        @Override
        public void addSource(@NonNull final LiveEvent<T> source) {
            super.addSource(source);
        }

        @MainThread
        public  <S> void removeSource(@NonNull final LiveData<S> source) {
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
