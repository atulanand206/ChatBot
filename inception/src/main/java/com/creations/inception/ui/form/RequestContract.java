package com.creations.inception.ui.form;

import com.creations.inception.ui.blogger.BloggerViewModel;
import com.creations.mvvm.ui.blocks.board.BoardViewModel;
import com.creations.mvvm.ui.drawer.DrawerViewModel;
import com.creations.mvvm.ui.navigation.NavigationBarViewModel;
import com.creations.mvvm.viewmodel.IMVVMViewModel;

import androidx.annotation.NonNull;

public interface RequestContract {

    interface ViewModel extends IMVVMViewModel {
        @NonNull
        BloggerViewModel getBlogger();

        @NonNull
        NavigationBarViewModel getNavigation();

        @NonNull
        DrawerViewModel getDrawer();

        @NonNull
        BoardViewModel getBoard();
    }

    interface InteractionListener {

        void setStatusBarColr(Integer colorResId);
    }
}
