package com.creations.mvvm.ui.drawer;

import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuContract;

import androidx.annotation.NonNull;

public interface DrawerContract {

    interface ViewModel<T extends Props> extends MenuContract.ViewModel<T> {


        void openDrawer();

        void closeDrawer();

        @NonNull
        LiveRunnable getOpenDrawerEvent();

        @NonNull
        LiveRunnable getCloseDrawerEvent();
    }

}
