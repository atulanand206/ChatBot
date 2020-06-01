package com.creations.bang.ui.bang;

import com.creations.bang.ui.card.CardAdapter;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuContract;

import androidx.annotation.NonNull;

public interface BangContract {

    interface ViewModel<T extends Props> extends MenuContract.ViewModel<T> {

        @NonNull
        CardAdapter getCardAdapter();
    }

}
