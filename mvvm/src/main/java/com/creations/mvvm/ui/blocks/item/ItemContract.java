package com.creations.mvvm.ui.blocks.item;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

public interface ItemContract {

    interface ViewModel<T extends Props> extends IRecyclerViewModel<T> {

    }

}
