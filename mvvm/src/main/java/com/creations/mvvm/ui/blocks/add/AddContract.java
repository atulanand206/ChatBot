package com.creations.mvvm.ui.blocks.add;

import com.creations.mvvm.live.LiveEvent;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.blocks.board.BoardContract;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

import androidx.annotation.NonNull;

public interface AddContract {

    interface ViewModel<T extends Props> extends IRecyclerViewModel<T> {

        int CLICK_BACKGROUND = 0;
        int CLICK_ADD_BUTTON = 1;
        int CLICK_FOREGROUND = 2;
        int CLICK_ADD_ROW = 3;
        @NonNull
        BoardContract.ViewModel getBoardViewModel();

        @NonNull
        LiveEvent.Mutable<Props> getAddDoneEvent();

        @NonNull
        LiveEvent.Mutable<Props> getAddCancelEvent();
    }

}
