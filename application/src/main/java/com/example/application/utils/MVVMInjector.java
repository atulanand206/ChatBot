package com.example.application.utils;

import java.util.Map;

import javax.inject.Provider;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjector;

public interface MVVMInjector {

    @Nullable
    Map<Class<? extends Fragment>, Provider<AndroidInjector.Builder<? extends Fragment>>> getInjectorFactories();

    @NonNull
    <T extends Fragment, B extends AndroidInjector.Builder<T>> B getBuilder(Class<T> klass, Class<B> builderKlass);

}
