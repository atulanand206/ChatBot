package com.creations.inception.ui.form;

import com.creations.inception.ui.blogger.BloggerViewModel;
import com.creations.mvvm.form.navigation.NavigationBarViewModel;
import com.creations.mvvm.viewmodel.IMVVMViewModel;

import androidx.annotation.NonNull;

public interface RequestContract {

    interface ViewModel extends IMVVMViewModel {



        @NonNull
        BloggerViewModel getBlogger();

        @NonNull
        NavigationBarViewModel getNavigation();

    }

    interface InteractionListener {

        void setStatusBarColr(Integer colorResId);
    }
}
