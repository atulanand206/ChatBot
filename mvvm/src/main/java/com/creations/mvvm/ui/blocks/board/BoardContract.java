package com.creations.mvvm.ui.blocks.board;

import com.creations.mvvm.models.blocks.Board;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

import androidx.annotation.NonNull;

public interface BoardContract {

    interface ViewModel extends IRecyclerViewModel {

        void setData(@NonNull Board board);

        @NonNull
        BoardAdapter getAdapter();
    }

}
