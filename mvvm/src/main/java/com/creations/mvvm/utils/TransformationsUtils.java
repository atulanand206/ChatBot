package com.creations.mvvm.utils;

import com.creations.condition.Preconditions;
import com.creations.mvvm.live.MediatorLiveData;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;

public class TransformationsUtils {

    public interface NonNullSourceMap<T, U> {

        @Nullable
        U map(@NonNull final T t);

    }

    public interface NonNullMap<T, U> {

        @NonNull
        U map(@NonNull final T t);

    }

    public interface NullableMap<T, U> {

        @Nullable
        U map(@Nullable final T t);

    }

    public interface NullableSourceMap<T, U> {

        @NonNull
        U map(@Nullable final T t);

    }

    @NonNull
    public static <T, U> NullableMap<T, U> toNullableMap(@NonNull final NullableSourceMap<T, U> map) {
        Preconditions.requiresNonNull(map, "NullableSourceMap");

        return map::map;
    }

    @NonNull
    public static <T, U> NullableMap<T, U> toNullableMap(@NonNull final NonNullSourceMap<T, U> map) {
        Preconditions.requiresNonNull(map, "NonNullMapSource");
        return t -> {
            if (t == null) {
                return null;
            } else {
                return map.map(t);
            }
        };
    }

    @NonNull
    public static <T, U> NullableSourceMap<T, U>  toNullableSourceMap(@NonNull final NonNullMap<T, U> map,
                                                                      @NonNull final U defaultValue) {
        Preconditions.requiresNonNull(map, "NonNullMap");
        Preconditions.requiresNonNull(defaultValue, "DefaultValue");

        return t -> {
            if (t == null) {
                return defaultValue;
            } else {
                return map.map(t);
            }
        };
    }

    @NonNull
    public static <T, U> NonNullSourceMap<T, U> toNonNullSourceMap(@NonNull final NonNullMap<T, U> map) {
        Preconditions.requiresNonNull(map, "NonNullMap");

        return map::map;
    }

    @NonNull
    public static <T, U> NullableMap<T, U> toNullableMap(@NonNull final NonNullMap<T, U> map) {
        Preconditions.requiresNonNull(map, "NonNullMap");

        return toNullableMap(toNonNullSourceMap(map));
    }

    @NonNull
    public static <T, U> NonNullMap<T, U> toNonNullMap(@NonNull final NullableSourceMap<T, U> map) {
        Preconditions.requiresNonNull(map, "NullableSourceMap");

        return map::map;
    }

    @NonNull
    public static <T, U> NonNullSourceMap<T, U> toNonNullSourceMap(@NonNull final NullableMap<T, U> map) {
        Preconditions.requiresNonNull(map, "NullableMap");

        return map::map;
    }

    @NonNull
    public static <T, U> Function<T, U> toFunction(@NonNull final NullableMap<T, U> map) {
        return map::map;
    }

    @NonNull
    public static <T, U> LiveData<U> mapNonNullSource(@NonNull final LiveData<T> source,
                                                      @NonNull final NonNullSourceMap<T, U> map) {
        Preconditions.requiresNonNull(source, "LiveDataSource");
        Preconditions.requiresNonNull(map, "NonNullMapOfTtoU");

        return Transformations.map(source, toFunction(toNullableMap(map)));
    }

    @NonNull
    public static <T, U> LiveData<U> switchMapNonNullSource(@NonNull final LiveData<T> source,
                                                            @NonNull final NonNullSourceMap<T, LiveData<U>> map) {
        Preconditions.requiresNonNull(source, "LiveDataSource");
        Preconditions.requiresNonNull(map, "NonNullMapOfTtoLiveDataOfU");

        return Transformations.switchMap(source, toFunction(toNullableMap(map)));
    }

    public static <T, U> void addNonNullMapSource(@NonNull final MediatorLiveData<U> liveData,
                                                  @NonNull final LiveData<T> source,
                                                  @NonNull final NonNullSourceMap<T, U> map) {
        liveData.addSource(source, t -> {
            if (t == null) {
                liveData.postValue(null);
            } else {
                liveData.postValue(map.map(t));
            }
        });
    }

    public static <T, U> void addNullableMapSource(@NonNull final MediatorLiveData<U> liveData,
                                                   @NonNull final LiveData<T> source,
                                                   @NonNull final NullableMap<T, U> map) {
        liveData.addSource(source, t -> liveData.postValue(map.map(t)));
    }

}
