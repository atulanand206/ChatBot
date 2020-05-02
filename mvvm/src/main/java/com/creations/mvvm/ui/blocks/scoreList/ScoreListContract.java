package com.creations.mvvm.ui.blocks.scoreList;

import com.creations.mvvm.databinding.CardBlocksScoreItemBinding;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.blocks.scoreItem.ScoreItemContract;
import com.creations.mvvm.ui.menu.MenuContract;

import androidx.annotation.NonNull;

public interface ScoreListContract {

    interface ViewModel<T extends Props> extends MenuContract.ViewModel<T> {

        @NonNull
        ScoreListAdapter<ScoreItemContract.ViewModel, CardBlocksScoreItemBinding> getAdapter();
    }

}
