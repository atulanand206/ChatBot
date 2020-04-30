package com.creations.mvvm.ui.blocks.container;

import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.animate.IAnimatorViewModel;
import com.creations.mvvm.ui.blocks.add.AddContract;
import com.creations.mvvm.ui.blocks.board.BoardContract;
import com.creations.mvvm.ui.blocks.score.ScoreContract;
import com.creations.mvvm.ui.blocks.word.WordViewModel;

import androidx.annotation.NonNull;

public interface ContainerContract {

    interface ViewModel<T extends Props> extends IAnimatorViewModel<T> {

        int MAX_ROWS = 10;

        @NonNull
        AddContract.ViewModel getAddViewModel();

        @NonNull
        MutableLiveData<Integer> getBorderWidth();

        @NonNull
        BoardContract.ViewModel getBoardViewModel();

        @NonNull
        ScoreContract.ViewModel getScoreViewModel();

        @NonNull
        WordViewModel getWordViewModel();

        @NonNull
        MutableLiveData<Integer> getAddVisibility();
    }

}
