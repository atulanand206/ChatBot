package com.creations.mvvm.live;

import com.creations.condition.Preconditions;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

// TODO: Implement unlock

/**
 * A {@link MediatorLiveData<T>} that can lock so it doesn't update from its sources anymore.
 * Effectively it can turn itself into a {@link MutableLiveData<T>}.
 *
 * @param <T> The type of data that will be stored.
 */
public class LockingMediatorLiveData<T> extends MediatorLiveData<T> {

    @NonNull
    private final Set<LiveData<?>> mDataSources = Collections.newSetFromMap((Map)new WeakHashMap<>());
    private boolean mLocked = false;

    public LockingMediatorLiveData() {
        super();
    }

    public LockingMediatorLiveData(@Nullable final T initialValue) {
        super(initialValue);
    }

    @MainThread
    @Override
    public <S> void addSource(@NonNull LiveData<S> source, @NonNull Observer<? super S> onChanged) {
        if(!mLocked) {
            Preconditions.requiresNonNull(source, "LiveDataSource");
            Preconditions.requiresNonNull(onChanged, "OnChanged");

            synchronized (mDataSources) {
                mDataSources.add(source);
            }
            super.addSource(source, onChanged);
        }
    }

    @MainThread
    @Override
    public <S> void removeSource(@NonNull LiveData<S> source) {
        Preconditions.requiresNonNull(source, "LiveDataSource");

        synchronized (mDataSources) {
            mDataSources.remove(source);
        }
        super.removeSource(source);
    }

    public void lock() {
        mLocked = true;
        synchronized (mDataSources) {
            for (LiveData<?> source : mDataSources) {
                removeSource(source);
            }
        }
    }

}
