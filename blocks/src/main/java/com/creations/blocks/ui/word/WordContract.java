package com.creations.blocks.ui.word;

import com.creations.blocks.models.Arr;
import com.creations.blocks.models.Cell;
import com.creations.blocks.models.Word;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuContract;
import com.creations.tools.callback.ObjectResponseCallback;

import java.util.List;

import androidx.annotation.NonNull;

public interface WordContract {

    interface ViewModel<T extends Props> extends MenuContract.ViewModel<T> {

        @NonNull
        List<Cell> getCells();

        void valid(@NonNull final ObjectResponseCallback<Word> callback);

        String getWord();

        void refresh(Arr selections);
    }

}
