package com.creations.mvvm.ui.blocks.row;

import com.creations.mvvm.models.blocks.Row;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

import androidx.annotation.NonNull;

public interface RowContract {

    interface ViewModel extends IRecyclerViewModel {

        void setData(@NonNull Row rowInfo);

        @NonNull
        RowAdapter getAdapter();
    }

}
