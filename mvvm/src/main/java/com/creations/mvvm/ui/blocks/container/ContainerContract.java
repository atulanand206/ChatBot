package com.creations.mvvm.ui.blocks.container;

import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.animate.IAnimatorViewModel;
import com.creations.mvvm.ui.blocks.add.AddContract;
import com.creations.mvvm.ui.blocks.board.BoardContract;

import androidx.annotation.NonNull;

public interface ContainerContract {

    interface ViewModel<T extends Props> extends IAnimatorViewModel<T> {

        @NonNull
        AddContract.ViewModel getAddViewModel();

        @NonNull
        MutableLiveData<Integer> getBorderWidth();

        @NonNull
        BoardContract.ViewModel getBoardViewModel();
    }

}
