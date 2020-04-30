package com.creations.mvvm.ui.blocks.word;

import android.app.Application;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.blocks.Cell;
import com.creations.mvvm.models.blocks.Word;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.blocks.CellViewModel;
import com.creations.mvvm.ui.menu.MenuViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class WordViewModel extends MenuViewModel<Props> implements WordContract.ViewModel<Props> {

    @NonNull
    private final List<Cell> mCells = new ArrayList<>();

    public WordViewModel(@NonNull final Application application,
                         @NonNull final CellViewModel.Factory cellFactory,
                         @NonNull final Word props) {
        super(application, props);

    }

    @Override
    public void addCell(Cell cell) {
        mCells.add(cell);
        getToastEvent().postEvent(String.valueOf(cell.getCharacter()));
    }

    @Override
    public void removeFirst() {
        if (mCells.isEmpty())
            return;
        mCells.remove(0);
    }

    @Override
    public void removeLast() {
        if (mCells.isEmpty())
            return;
        mCells.remove(mCells.size()-1);
    }

    public static class Factory extends MVVMViewModel.Factory<WordViewModel> {

        @NonNull
        private final Word mProps;

        @NonNull
        private final CellViewModel.Factory mCellFactory;

        public Factory(@NonNull final Application application,
                       @NonNull final CellViewModel.Factory cellFactory,
                       @NonNull final Word props) {
            super(WordViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
            mCellFactory = Preconditions.requiresNonNull(cellFactory, "CellFactory");
        }

        @NonNull
        @Override
        public WordViewModel create() {
            return new WordViewModel(mApplication, mCellFactory, mProps);
        }
    }
}
