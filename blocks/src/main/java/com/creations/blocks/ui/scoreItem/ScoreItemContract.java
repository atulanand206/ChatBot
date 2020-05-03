package com.creations.blocks.ui.scoreItem;

import com.creations.blocks.models.ScoreItem;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

public interface ScoreItemContract {

    interface ViewModel<T extends ScoreItem> extends IRecyclerViewModel<T> {

    }

}
