package com.creations.mvvm.live;

import com.creations.condition.Preconditions;

import java.util.Map;

import androidx.annotation.CallSuper;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.internal.SafeIterableMap;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

/**
 * A {@link MutableLiveData<T>} that can update based on other {@link androidx.lifecycle.LiveData}
 * sources in a lifecycle safe manner.
 *
 * This is used to chain LiveData instances in a dependency graph.
 * Very useful since ViewModels can never observe a {@link androidx.lifecycle.LiveData}'s
 * value themselves.
 *
 * @param <T> The data type that will be stored
 */
public class MediatorLiveData<T> extends MutableLiveData<T> {

    @NonNull
    private final SafeIterableMap<LiveData<?>, Source<?>> mSources = new SafeIterableMap<>();

    public MediatorLiveData() { }

    public MediatorLiveData(@Nullable T initialValue) {
        super(initialValue);
    }

    @Override
    public void setValue(@Nullable final T value) {
        super.setValue(value);
    }

    @MainThread
    public <S> void addSource(@NonNull final LiveData<S> source,
                              @NonNull final Observer<? super S> onChanged) {
        Preconditions.requiresNonNull(source, "LiveDataSource");
        Preconditions.requiresNonNull(onChanged, "OnChanged");

        Source e = new Source<>(source, onChanged);
        Source<?> existing = mSources.putIfAbsent(source, e);
        if (existing != null) {
            if(existing.mListener != onChanged) {
                throw new IllegalArgumentException(
                        "This source was already added with the different observer");
            }
            return;
        }
        if (hasActiveObservers()) {
            e.plug();
        }
    }

    @MainThread
    public <S> void removeSource(@NonNull final LiveData<S> source) {
        Preconditions.requiresNonNull(source, "LiveDataSource");

        Source<?> sourceInternal = mSources.remove(source);
        if (sourceInternal != null) {
            sourceInternal.unplug();
        }
    }

    public void clearSources() {
        for(Map.Entry<LiveData<?>, Source<?>> source : mSources) {
            mSources.remove(source.getKey());
        }
    }

    @CallSuper
    @Override
    protected void onActive() {
        for (Map.Entry<LiveData<?>, Source<?>> source : mSources) {
            source.getValue().plug();
        }
    }

    @CallSuper
    @Override
    protected void onInactive() {
        for (Map.Entry<LiveData<?>, Source<?>> source : mSources) {
            source.getValue().unplug();
        }
    }

    public class Source<S> implements Observer<S> {
        @NonNull
        final LiveData<S> mLiveData;
        @NonNull
        final Observer<? super S> mListener;

        Source(@NonNull final LiveData<S> liveData,
               @NonNull final Observer<? super S> listener) {
            mLiveData = Preconditions.requiresNonNull(liveData, "LiveDataOfS");
            mListener = Preconditions.requiresNonNull(listener, "NonNullListenerOfS");
        }

        /*
        Source(@NonNull final LiveData<S> liveData,
               @NonNull final TransformationsUtils.NonNullSourceMap<? super S, T> map) {
            this(liveData, (LiveEvent.NonNullListener<? super S>)(s -> setValue(map.map(s)));

            Preconditions.requiresNonNull(map, "NonNullMapSourceOfStoT");
        }
        */

        void plug() {
            mLiveData.observeForever(this);
        }

        void unplug() {
            mLiveData.removeObserver(this);
        }

        @Override
        public void onChanged(@Nullable final S s) {
            mListener.onChanged(s);
        }
    }
}
