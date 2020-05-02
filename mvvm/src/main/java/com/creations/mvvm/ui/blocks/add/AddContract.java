package com.creations.mvvm.ui.blocks.add;

import com.creations.mvvm.R;
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
        int CLICK_CANCEL_WORD = 4;
        int CLICK_START_BOARD = 5;
        int CLICK_EXIT_BOARD = 8;
        int CLICK_ADD_WORD = 5;
        int CLICK_REFRESH = 6;
        int CLICK_LOAD_BOARD = 7;
        int COLOR_WHITE = R.color.white;
        int COLOR_NORMAL = R.color.colorPrimary;
        int COLOR_ADD_ERROR = R.color.pal_red;
        int COLOR_ADD_GO = R.color.pal_green;
        int COLOR_ADD_CLEAR = R.color.pal_blue;
        int COLOR_ADD_GREY = R.color.pal_grey;
        int COLOR_MATCH = R.color.pal_pink;
        int COLOR_NEAR = R.color.colorPrimary;
        @NonNull
        BoardContract.ViewModel getBoardViewModel();

        @NonNull
        LiveEvent.Mutable<Props> getAddDoneEvent();

        @NonNull
        LiveEvent.Mutable<Props> getAddCancelEvent();
    }

}
