package com.creations.mvvm.ui.navigation;

import com.creations.mvvm.models.navigation.NavigationBarProps;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

import androidx.annotation.NonNull;

public interface NavigationBarContract {

    interface ViewModel extends IRecyclerViewModel {



        void setProps(@NonNull final NavigationBarProps props);

        /**
         * @return the recycler adapter being used by the teams list.
         */
        @NonNull
        NavigationBarAdapter getAdapter();

        /**
         * @return the list of certificates to be added on the recycler view.
         */
        @NonNull
        NavigationBarProps getProps();

    }

}
