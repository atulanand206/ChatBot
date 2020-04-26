package com.creations.inception.ui.form;

import android.app.Application;

import com.creations.condition.Preconditions;
import com.creations.inception.ui.blogger.BloggerViewModel;
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

    public RequestViewModel(@NonNull final Application application,
                            @NonNull final NavigationBarViewModel.Factory navigationFactory,
                            @NonNull final BloggerViewModel.Factory bloggerFactory,
                            @NonNull final DrawerViewModel.Factory drawerFactory) {
        super(application);
        mNavigation = navigationFactory.create();
        mBlogger = bloggerFactory.create();
        mDrawer = drawerFactory.create();
        mDrawer.openDrawer();
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

    public static class RequestFactory extends Factory<RequestViewModel> {

        @NonNull
        private final BloggerViewModel.Factory mBloggerFactory;
        @NonNull
        private final NavigationBarViewModel.Factory mNavigationFactory;
        @NonNull
        private final DrawerViewModel.Factory mDrawerFactory;

        public RequestFactory(@NonNull final Application application,
                              @NonNull final NavigationBarViewModel.Factory navigationFactory,
                              @NonNull final BloggerViewModel.Factory bloggerFactory,
                              @NonNull final DrawerViewModel.Factory drawerFactory) {
            super(RequestViewModel.class, application);
            mNavigationFactory = Preconditions.requiresNonNull(navigationFactory, "NavigationFactory");
            mBloggerFactory = Preconditions.requiresNonNull(bloggerFactory, "BloggerFactory");
            mDrawerFactory = Preconditions.requiresNonNull(drawerFactory, "DrawerFactory");
        }

        @NonNull
        @Override
        public RequestViewModel create() {
            return new RequestViewModel(mApplication, mNavigationFactory, mBloggerFactory, mDrawerFactory);
        }
    }
}