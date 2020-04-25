package com.example.application.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjector;

public interface MVVMInjector {

    @NonNull
    <T extends Fragment, B extends AndroidInjector.Builder<T>> B getBuilder(Class<T> klass, Class<B> builderKlass);

}
