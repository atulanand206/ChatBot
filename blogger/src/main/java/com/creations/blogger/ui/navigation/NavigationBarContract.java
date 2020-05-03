package com.creations.blogger.ui.navigation;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

import androidx.annotation.NonNull;

public interface NavigationBarContract {

    interface ViewModel<T extends Props> extends IRecyclerViewModel<T> {

        /**
         * @return the recycler adapter being used by the teams list.
         */
        @NonNull
        NavigationBarAdapter getAdapter();

    }

}
