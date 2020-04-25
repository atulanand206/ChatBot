package com.example.dagger.key;

import androidx.fragment.app.Fragment;
import dagger.MapKey;

@MapKey
public @interface CustomFragmentKey {
    Class<? extends Fragment> value();
}
