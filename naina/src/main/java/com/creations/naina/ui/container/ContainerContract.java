package com.creations.naina.ui.container;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuContract;

public interface ContainerContract {

    interface ViewModel<T extends Props> extends MenuContract.ViewModel<T> {

    }

    interface InteractionListener {

    }
}
