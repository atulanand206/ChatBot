package com.creations.mvvm.ui.blocks.container;

import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.animate.IAnimatorViewModel;
import com.creations.mvvm.ui.blocks.add.AddContract;
import com.creations.mvvm.ui.blocks.board.BoardContract;
import com.creations.mvvm.ui.blocks.done.DoneViewModel;
import com.creations.mvvm.ui.blocks.score.ScoreContract;

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
        DoneViewModel getDoneViewModel();

        @NonNull
        LiveData<Integer> getAddVisibility();

        @NonNull
        LiveData<Integer> getCloseVisibility();

        @NonNull
        LiveData<Integer> getTickVisibility();
    }

}
