package com.creations.inception.ui.form;

import android.app.Application;

import com.creations.blocks.models.Preset;
import com.creations.blocks.ui.container.ContainerViewModel;
import com.creations.blogger.model.navigation.NavigationBarProps;
import com.creations.blogger.ui.blogger.BloggerViewModel;
import com.creations.blogger.ui.navigation.NavigationBarViewModel;
import com.creations.condition.Preconditions;
import com.creations.mvvm.ui.drawer.DrawerViewModel;
import com.creations.mvvm.ui.menu.MenuViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;

public class RequestViewModel extends MenuViewModel<NavigationBarProps> implements RequestContract.ViewModel<NavigationBarProps> {

    private static final String TAG = RequestViewModel.class.getName();

    @NonNull
    private NavigationBarViewModel mNavigation;
    @NonNull
    private BloggerViewModel mBlogger;
    @NonNull
    private DrawerViewModel mDrawer;
    @NonNull
    private final ContainerViewModel mBoard;

    public RequestViewModel(@NonNull final Application application,
                            @NonNull final NavigationBarViewModel.Factory navigationFactory,
                            @NonNull final BloggerViewModel.Factory bloggerFactory,
                            @NonNull final DrawerViewModel.Factory drawerFactory,
                            @NonNull final ContainerViewModel.Factory boardFactory,
                            @NonNull final Preset preset) {
        super(application, new NavigationBarProps());
        mBoard = boardFactory.create();
        mBoard.getPresetViewModel().setProps(preset);
        mContextCallback.addSource(mBoard.getContextCallback());
    }

    @NonNull
    @Override
    public BloggerViewModel getBlogger() {
        return mBlogger;
    }

    @NonNull
    @Override
    public NavigationBarViewModel getNavigation() {
        return mNavigation;
    }

    @NonNull
    @Override
    public DrawerViewModel getDrawer() {
        return mDrawer;
    }

    @NonNull
    @Override
    public ContainerViewModel getBoard() {
        return mBoard;
    }

    public static class RequestFactory extends MVVMViewModel.Factory<RequestViewModel> {

        @NonNull
        private final BloggerViewModel.Factory mBloggerFactory;
        @NonNull
        private final NavigationBarViewModel.Factory mNavigationFactory;
        @NonNull
        private final DrawerViewModel.Factory mDrawerFactory;
        @NonNull
        private final ContainerViewModel.Factory mBoardFactory;
        @NonNull
        private final Preset mPreset;

        public RequestFactory(@NonNull final Application application,
                              @NonNull final NavigationBarViewModel.Factory navigationFactory,
                              @NonNull final BloggerViewModel.Factory bloggerFactory,
                              @NonNull final DrawerViewModel.Factory drawerFactory,
                              @NonNull final ContainerViewModel.Factory boardFactory,
                              @NonNull final Preset preset) {
            super(RequestViewModel.class, application);
            mNavigationFactory = Preconditions.requiresNonNull(navigationFactory, "NavigationFactory");
            mBloggerFactory = Preconditions.requiresNonNull(bloggerFactory, "BloggerFactory");
            mDrawerFactory = Preconditions.requiresNonNull(drawerFactory, "DrawerFactory");
            mBoardFactory = Preconditions.requiresNonNull(boardFactory, "BoardFactory");
            mPreset = Preconditions.requiresNonNull(preset, "Preset");
        }

        @NonNull
        @Override
        public RequestViewModel create() {
            return new RequestViewModel(mApplication, mNavigationFactory,
                    mBloggerFactory, mDrawerFactory, mBoardFactory, mPreset);
        }
    }
}