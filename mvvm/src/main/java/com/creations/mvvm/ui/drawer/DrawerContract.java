package com.creations.mvvm.ui.drawer;

import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.viewmodel.IMVVMViewModel;

import androidx.annotation.NonNull;

public interface DrawerContract {

    interface ViewModel extends IMVVMViewModel {


        void openDrawer();

        void closeDrawer();

        @NonNull
        LiveRunnable getOpenDrawerEvent();

        @NonNull
        LiveRunnable getCloseDrawerEvent();
    }

}
