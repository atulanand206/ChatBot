package com.creations.mvvm.ui.blocks.options;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

public interface OptionsContract {

    interface ViewModel<T extends Props> extends IRecyclerViewModel<T> {

    }

}
