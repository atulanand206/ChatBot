package com.creations.mvvm.ui.blocks.board;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

import androidx.annotation.NonNull;

public interface BoardContract {

    interface ViewModel<T extends Props> extends IRecyclerViewModel<T> {

        @NonNull
        BoardAdapter getAdapter();
    }

}
