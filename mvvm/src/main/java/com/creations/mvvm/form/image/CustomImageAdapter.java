package com.creations.mvvm.form.image;

import com.creations.mvvm.form.navigation.NavigationBarAdapter;
import com.creations.mvvm.form.navigation.NavigationBarContract;

import androidx.annotation.NonNull;

public class CustomImageAdapter extends NavigationBarAdapter {
    public CustomImageAdapter(int layoutId,
                              @NonNull NavigationBarContract.ViewModel navigationBarViewModel) {
        super(layoutId, navigationBarViewModel);
    }
}
