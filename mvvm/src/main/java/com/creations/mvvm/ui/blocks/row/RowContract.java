package com.creations.mvvm.ui.blocks.row;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

import androidx.annotation.NonNull;

public interface RowContract {

    interface ViewModel<T extends Props> extends IRecyclerViewModel<T> {

        @NonNull
        RowAdapter getAdapter();
    }

}
