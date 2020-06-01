package com.creations.bang.ui.card;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

public interface CardContract {

    interface ViewModel<T extends Props> extends IRecyclerViewModel<T> {

    }

}
