package com.creations.blocks.ui.home;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuContract;

public interface HomeContract {

    interface ViewModel<T extends Props> extends MenuContract.ViewModel<T> {

    }

}
