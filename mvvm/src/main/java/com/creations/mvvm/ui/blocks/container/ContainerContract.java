package com.creations.mvvm.ui.blocks.container;

import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.blocks.add.AddContract;
import com.creations.mvvm.ui.blocks.board.BoardContract;
import com.creations.mvvm.ui.blocks.done.DoneContract;
import com.creations.mvvm.ui.blocks.preset.PresetContract;
import com.creations.mvvm.ui.blocks.score.ScoreContract;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public interface ContainerContract {

    interface ViewModel<T extends Props> extends IRecyclerViewModel<T> {

        int MAX_ROWS = 10;
        int MIN_COLUMNS = 10;

        @NonNull
        AddContract.ViewModel getAddViewModel();

        @NonNull
        MutableLiveData<Integer> getBorderWidth();

        @NonNull
        BoardContract.ViewModel getBoardViewModel();

        @NonNull
        ScoreContract.ViewModel getScoreViewModel();

        @NonNull
        DoneContract.ViewModel getDoneViewModel();

        @NonNull
        PresetContract.ViewModel getPresetViewModel();

        @NonNull
        LiveData<Integer> getActionVisibility();

        @NonNull
        LiveData<Integer> getAddVisibility();

        @NonNull
        LiveData<Integer> getCloseVisibility();

        @NonNull
        LiveData<Integer> getTickVisibility();

        @NonNull
        LiveData<Integer> getRefreshVisibility();

        @NonNull
        MutableLiveData<Integer> getPossibleScore();
    }

}
