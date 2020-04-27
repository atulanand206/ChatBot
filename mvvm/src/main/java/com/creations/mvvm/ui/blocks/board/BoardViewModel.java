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
public class BoardViewModel extends RecyclerViewModel<Board> implements BoardContract.ViewModel<Board> {

    @NonNull
    private final RowViewModel.Factory mRowFactory;
    @NonNull
    private final BoardAdapter<RowContract.ViewModel, CardBlocksRowBinding> adapter = new BoardAdapter<>(viewModel -> {

    }, R.layout.card_blocks_row);

    public BoardViewModel(@NonNull final Application application,
                          @NonNull final RowViewModel.Factory rowFactory,
                          @NonNull final Board board) {
        super(application, board);
        mRowFactory = Preconditions.requiresNonNull(rowFactory, "Factory");
        setProps(BoardUtils.testBoard(9));
        setTopColor(R.color.colorPrimary);
    }

    @Override
    public void setProps(@NonNull final Board board) {
        super.setProps(board);
        adapter.clearItems();
        for (Row row : board.getRows()) {
            RowViewModel viewModel = mRowFactory.create();
            viewModel.setProps(row);
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
