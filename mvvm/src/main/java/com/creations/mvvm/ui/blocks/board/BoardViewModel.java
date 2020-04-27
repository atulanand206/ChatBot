package com.creations.mvvm.ui.blocks.board;

import android.app.Application;

import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.databinding.CardBlocksRowBinding;
import com.creations.mvvm.models.blocks.Board;
import com.creations.mvvm.models.blocks.Row;
import com.creations.mvvm.ui.blocks.row.RowContract;
import com.creations.mvvm.ui.blocks.row.RowViewModel;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.utils.BoardUtils;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class BoardViewModel extends RecyclerViewModel implements BoardContract.ViewModel {

    @NonNull
    private Board mBoardProps;
    @NonNull
    private final RowViewModel.Factory mRowFactory;
    @NonNull
    private final BoardAdapter<RowContract.ViewModel, CardBlocksRowBinding> adapter = new BoardAdapter<>(viewModel -> {

    }, R.layout.card_blocks_row);

    public BoardViewModel(@NonNull final Application application,
                          @NonNull final RowViewModel.Factory rowFactory,
                          @NonNull final Board board) {
        super(application);
        mRowFactory = Preconditions.requiresNonNull(rowFactory, "Factory");
        setData(BoardUtils.testBoard(9));
        setTopColor(R.color.colorPrimary);
    }

    @Override
    public void setData(@NonNull final Board board) {
        adapter.clearItems();
        mBoardProps = Preconditions.requiresNonNull(board, "Info");
        for (Row row : board.getRows()) {
            RowViewModel viewModel = mRowFactory.create();
            viewModel.setData(row);
            adapter.addItem(viewModel);
        }
    }

    @NonNull
    @Override
    public BoardAdapter getAdapter() {
        return adapter;
    }

    public static class Factory extends MVVMViewModel.Factory<BoardViewModel> {

        @NonNull
        private final RowViewModel.Factory mRowFactory;

        @NonNull
        private final Board mProps;


        public Factory(@NonNull final Application application,
                       @NonNull final RowViewModel.Factory rowFactory,
                       @NonNull final Board props) {
            super(BoardViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
            mRowFactory = Preconditions.requiresNonNull(rowFactory, "RowFactory");
        }

        @NonNull
        @Override
        public BoardViewModel create() {
            return new BoardViewModel(mApplication, mRowFactory, mProps);
        }
    }
}
