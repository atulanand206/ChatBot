package com.creations.mvvm.ui.blocks.word;

import com.creations.mvvm.models.blocks.Cell;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuContract;

public interface WordContract {

    interface ViewModel<T extends Props> extends MenuContract.ViewModel<T> {

        void addCell(Cell cell);

        void removeFirst();

        void removeLast();
    }

}
