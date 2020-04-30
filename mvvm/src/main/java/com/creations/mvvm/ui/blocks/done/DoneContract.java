package com.creations.mvvm.ui.blocks.done;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuContract;

public interface DoneContract {

    interface ViewModel<T extends Props> extends MenuContract.ViewModel<T> {

        void done(String word);
    }

}
