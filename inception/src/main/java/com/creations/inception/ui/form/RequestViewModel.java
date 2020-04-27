package com.creations.inception.ui.form;

import android.app.Application;

import com.creations.condition.Preconditions;
import com.creations.inception.ui.blogger.BloggerViewModel;
import com.creations.mvvm.ui.blocks.board.BoardViewModel;
import com.creations.mvvm.ui.drawer.DrawerViewModel;
import com.creations.mvvm.ui.navigation.NavigationBarViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;

public class RequestViewModel extends MVVMViewModel implements RequestContract.ViewModel {

    private static final String TAG = RequestViewModel.class.getName();

    @NonNull
    private final NavigationBarViewModel mNavigation;
    @NonNull
    private final BloggerViewModel mBlogger;
    @NonNull
    private final DrawerViewModel mDrawer;
    @NonNull
    private final BoardViewModel mBoard;

    public RequestViewModel(@NonNull final Application application,
                            @NonNull final NavigationBarViewModel.Factory navigationFactory,
                            @NonNull final BloggerViewModel.Factory bloggerFactory,
                            @NonNull final DrawerViewModel.Factory drawerFactory,
                            @NonNull final BoardViewModel.Factory boardFactory) {
        super(application);
        mNavigation = navigationFactory.create();
        mBlogger = bloggerFactory.create();
        mDrawer = drawerFactory.create();
        mBoard = boardFactory.create();
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
    public BoardViewModel getBoard() {
        return mBoard;
    }

    public static class RequestFactory extends Factory<RequestViewModel> {

        @NonNull
        private final BloggerViewModel.Factory mBloggerFactory;
        @NonNull
        private final NavigationBarViewModel.Factory mNavigationFactory;
        @NonNull
        private final DrawerViewModel.Factory mDrawerFactory;
        @NonNull
        private final BoardViewModel.Factory mBoardFactory;

        public RequestFactory(@NonNull final Application application,
                              @NonNull final NavigationBarViewModel.Factory navigationFactory,
                              @NonNull final BloggerViewModel.Factory bloggerFactory,
                              @NonNull final DrawerViewModel.Factory drawerFactory,
                              @NonNull final BoardViewModel.Factory boardFactory) {
            super(RequestViewModel.class, application);
            mNavigationFactory = Preconditions.requiresNonNull(navigationFactory, "NavigationFactory");
            mBloggerFactory = Preconditions.requiresNonNull(bloggerFactory, "BloggerFactory");
            mDrawerFactory = Preconditions.requiresNonNull(drawerFactory, "DrawerFactory");
            mBoardFactory = Preconditions.requiresNonNull(boardFactory, "BoardFactory");
        }

        @NonNull
        @Override
        public RequestViewModel create() {
            return new RequestViewModel(mApplication, mNavigationFactory,
                    mBloggerFactory, mDrawerFactory, mBoardFactory);
        }
    }
}