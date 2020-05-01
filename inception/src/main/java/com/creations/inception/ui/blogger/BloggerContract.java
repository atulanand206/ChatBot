package com.creations.inception.ui.blogger;

import com.creations.mvvm.ui.animate.IAnimatorViewModel;
import com.creations.mvvm.ui.exp.NavigationRecycler;
import com.creations.mvvm.ui.image.ImageAdapter;
import com.creations.mvvm.ui.image.ImageViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public interface BloggerContract {

    interface ViewModel extends IAnimatorViewModel {

        @NonNull
        LiveData<ImageViewModel> getImageViewModel();

        @NonNull
        ImageAdapter getImageAdapter();

        @NonNull
        NavigationRecycler getNavigationAdapter();

        void onOverlayCloseClicked();
    }

}
