package com.creations.inception.ui.form;

import com.creations.inception.ui.blogger.BloggerViewModel;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.blocks.board.BoardViewModel;
import com.creations.mvvm.ui.drawer.DrawerViewModel;
import com.creations.mvvm.ui.menu.MenuContract;
import com.creations.mvvm.ui.navigation.NavigationBarViewModel;

import androidx.annotation.NonNull;

public interface RequestContract {

    interface ViewModel<T extends Props> extends MenuContract.ViewModel<T> {
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

        void setStatusBarColr(int colorResId);
    }
}
