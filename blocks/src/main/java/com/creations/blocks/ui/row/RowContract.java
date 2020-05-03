package com.creations.blocks.ui.row;

import com.creations.mvvm.live.LiveEvent;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

import androidx.annotation.NonNull;

public interface RowContract {

    interface ViewModel<T extends Props> extends IRecyclerViewModel<T> {

        @NonNull
        LiveEvent.Mutable getAddEvent();

        @NonNull
        RowAdapter getAdapter();

        void notifyDataSetChanged();
    }

}
