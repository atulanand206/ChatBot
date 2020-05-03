package com.creations.inception.ui.form;

import com.creations.blocks.ui.container.ContainerViewModel;
import com.creations.blogger.ui.blogger.BloggerViewModel;
import com.creations.blogger.ui.navigation.NavigationBarViewModel;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.drawer.DrawerViewModel;
import com.creations.mvvm.ui.menu.MenuContract;

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
        ContainerViewModel getBoard();
    }

    interface InteractionListener {

        void setStatusBarColr(int colorResId);
    }
}
