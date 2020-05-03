package com.creations.blocks.ui.container;

import com.creations.blocks.ui.add.AddContract;
import com.creations.blocks.ui.board.BoardContract;
import com.creations.blocks.ui.done.DoneContract;
import com.creations.blocks.ui.home.HomeContract;
import com.creations.blocks.ui.preset.PresetContract;
import com.creations.blocks.ui.score.ScoreContract;
import com.creations.blocks.ui.scoreList.ScoreListContract;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.animate.IAnimatorViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public interface ContainerContract {

    interface ViewModel<T extends Props> extends IAnimatorViewModel<T> {

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
        ScoreListContract.ViewModel getScoreListViewModel();

        @NonNull
        HomeContract.ViewModel getHomeViewModel();

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
