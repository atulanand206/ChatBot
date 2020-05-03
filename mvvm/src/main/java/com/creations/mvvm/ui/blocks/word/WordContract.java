package com.creations.mvvm.ui.blocks.word;

import com.creations.blogger.callback.ObjectResponseCallback;
import com.creations.mvvm.models.array.Arr;
import com.creations.mvvm.models.blocks.Cell;
import com.creations.mvvm.models.blocks.Word;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuContract;

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
