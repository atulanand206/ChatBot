package com.creations.inception.ui.blogger;

import com.creations.mvvm.form.image.ImageAdapter;
import com.creations.mvvm.form.image.ImageViewModel;
import com.creations.mvvm.viewmodel.IAnimatorViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public interface BloggerContract {

    interface ViewModel extends IAnimatorViewModel {

        @NonNull
        LiveData<String> getTitle();

        @NonNull
        LiveData<ImageViewModel> getImageViewModel();

        @NonNull
        ImageAdapter getImageAdapter();

        void onOverlayCloseClicked();
    }

}
