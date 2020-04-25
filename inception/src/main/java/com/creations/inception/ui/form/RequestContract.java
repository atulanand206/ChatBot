package com.creations.inception.ui.form;

import com.creations.mvvm.form.image.ImageAdapter;
import com.creations.mvvm.form.image.ImageViewModel;
import com.creations.mvvm.form.navigation.NavigationBarViewModel;
import com.creations.mvvm.viewmodel.IMVVMViewModel;

import androidx.annotation.NonNull;

public interface RequestContract {

    interface ViewModel extends IMVVMViewModel {

        @NonNull
        ImageViewModel getImageViewModel();

        @NonNull
        ImageAdapter getImageAdapter();

        @NonNull
        NavigationBarViewModel getNavigationBar();

    }

    interface InteractionListener {

    }
}
