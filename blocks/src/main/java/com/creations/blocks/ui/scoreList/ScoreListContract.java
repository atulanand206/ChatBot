package com.creations.blocks.ui.scoreList;

import com.creations.blocks.ui.scoreItem.ScoreItemContract;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuContract;
import com.example.blocks.databinding.CardBlocksScoreItemBinding;

import androidx.annotation.NonNull;

public interface ScoreListContract {

    interface ViewModel<T extends Props> extends MenuContract.ViewModel<T> {

        @NonNull
        ScoreListAdapter<ScoreItemContract.ViewModel, CardBlocksScoreItemBinding> getAdapter();
    }

}
