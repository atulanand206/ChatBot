package com.creations.mvvm.ui.blocks.board;

import com.creations.mvvm.live.LiveEvent;
import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.models.blocks.Row;
import com.creations.mvvm.models.blocks.RowWrapper;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.blocks.word.WordViewModel;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public interface BoardContract {

    interface ViewModel<T extends Props> extends IRecyclerViewModel<T> {

        @NonNull
        LiveRunnable.Mutable getAddRowEvent();

        @NonNull
        LiveEvent.Mutable<Props> getAddWordEvent();

        @NonNull
        LiveEvent.Mutable<RowWrapper> getAddCellEvent();

        @NonNull

        LiveData<Integer> getGridColor();

        @NonNull
        BoardAdapter getAdapter();

        @NonNull
        WordViewModel getWordViewModel();

        void addRow(@NonNull final Row props);

        void clear();

        String getWord();

        void refresh();
    }

}
