package com.creations.mvvm.ui.blank;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuContract;

public interface FreshContract {

    interface ViewModel<T extends Props> extends MenuContract.ViewModel<T> {

    }

}
