package com.creations.naina.ui.container;

import com.creations.bang.ui.bang.BangViewModel;
import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuContract;

import androidx.annotation.NonNull;

public interface ContainerContract {

    interface ViewModel<T extends Props> extends MenuContract.ViewModel<T> {

        @NonNull
        BangViewModel getBangViewModel();

        @NonNull
        LiveRunnable getUploadEvent();

        void onUploadClicked();
    }

    interface InteractionListener {

        void onUploadEventClicked();
    }
}
