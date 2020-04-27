package com.creations.mvvm.ui.drawer;

import android.app.Application;

import com.creations.condition.Preconditions;
import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.models.props.DrawerProps;
import com.creations.mvvm.ui.menu.MenuViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.example.application.R;

import androidx.annotation.NonNull;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class DrawerViewModel extends MenuViewModel<DrawerProps> implements DrawerContract.ViewModel<DrawerProps> {

    @NonNull
    private final LiveRunnable.Mutable mOpenDrawerEvent = new LiveRunnable.Mutable();

    @NonNull
    private final LiveRunnable.Mutable mCloseDrawerEvent = new LiveRunnable.Mutable();


    public DrawerViewModel(@NonNull final Application application,
                           @NonNull final DrawerProps drawerProps) {
        super(application, drawerProps);
        setId(R.id.left_drawer);
    }

    @Override
    public void openDrawer() {
        mOpenDrawerEvent.postEvent();
    }

    @Override
    public void closeDrawer() {
        mCloseDrawerEvent.postEvent();
    }

    @NonNull
    @Override
    public LiveRunnable getOpenDrawerEvent() {
        return mOpenDrawerEvent;
    }

    @NonNull
    @Override
    public LiveRunnable getCloseDrawerEvent() {
        return mCloseDrawerEvent;
    }

    public static class Factory extends MVVMViewModel.Factory<DrawerViewModel> {

        @NonNull
        private final DrawerProps mDrawerProps;

        public Factory(@NonNull final Application application,
                       @NonNull final DrawerProps drawerProps) {
            super(DrawerViewModel.class, application);
            mDrawerProps = Preconditions.requiresNonNull(drawerProps, "DrawerProps");
        }

        @NonNull
        @Override
        public DrawerViewModel create() {
            return new DrawerViewModel(mApplication, mDrawerProps);
        }
    }
}
