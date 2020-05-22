package com.creations.bang.ui.bang;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuContract;

public interface BangContract {

    interface ViewModel<T extends Props> extends MenuContract.ViewModel<T> {

    }

}
