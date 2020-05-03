package com.creations.blocks.ui.score;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuContract;

public interface ScoreContract {

    interface ViewModel<T extends Props> extends MenuContract.ViewModel<T> {

        void add(int lengthOfWord);
    }

}
