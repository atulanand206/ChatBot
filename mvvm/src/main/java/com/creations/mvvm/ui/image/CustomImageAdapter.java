package com.creations.mvvm.ui.image;

import com.creations.mvvm.ui.navigation.NavigationBarAdapter;
import com.creations.mvvm.ui.navigation.NavigationBarContract;

import androidx.annotation.NonNull;

public class CustomImageAdapter extends NavigationBarAdapter {
    public CustomImageAdapter(int layoutId,
                              @NonNull NavigationBarContract.ViewModel navigationBarViewModel) {
        super(layoutId, navigationBarViewModel);
    }
}
