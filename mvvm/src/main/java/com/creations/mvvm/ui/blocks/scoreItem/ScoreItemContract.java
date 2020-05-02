package com.creations.mvvm.ui.blocks.scoreItem;

import com.creations.mvvm.models.blocks.ScoreItem;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

public interface ScoreItemContract {

    interface ViewModel<T extends ScoreItem> extends IRecyclerViewModel<T> {

    }

}
