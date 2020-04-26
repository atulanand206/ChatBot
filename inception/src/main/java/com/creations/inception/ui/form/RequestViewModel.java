package com.creations.inception.ui.form;

import android.app.Application;

import com.creations.condition.Preconditions;
import com.creations.inception.ui.blogger.BloggerViewModel;
import com.creations.mvvm.form.navigation.NavigationBarViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;

public class RequestViewModel extends MVVMViewModel implements RequestContract.ViewModel {

    private static final String TAG = RequestViewModel.class.getName();

    @NonNull
    private final NavigationBarViewModel mNavigation;
    @NonNull
    private final BloggerViewModel mBlogger;

    public RequestViewModel(@NonNull final Application application,
                            @NonNull final NavigationBarViewModel.Factory navigationFactory,
                            @NonNull final BloggerViewModel.Factory bloggerFactory) {
        super(application);
        mNavigation = navigationFactory.create();
        mBlogger = bloggerFactory.create();
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

    public static class RequestFactory extends Factory<RequestViewModel> {

        @NonNull
        private final BloggerViewModel.Factory mBloggerFactory;
        @NonNull
        private final NavigationBarViewModel.Factory mNavigationFactory;

        public RequestFactory(@NonNull final Application application,
                              @NonNull final NavigationBarViewModel.Factory navigationFactory,
                              @NonNull final BloggerViewModel.Factory bloggerFactory) {
            super(RequestViewModel.class, application);
            mNavigationFactory = Preconditions.requiresNonNull(navigationFactory, "NavigationFactory");
            mBloggerFactory = Preconditions.requiresNonNull(bloggerFactory, "BloggerFactory");
        }

        @NonNull
        @Override
        public RequestViewModel create() {
            return new RequestViewModel(mApplication, mNavigationFactory, mBloggerFactory);
        }
    }
}