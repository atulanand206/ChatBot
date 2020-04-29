package com.creations.mvvm.ui.blocks.board;

import android.app.Application;

import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.databinding.CardBlocksRowBinding;
import com.creations.mvvm.live.LiveEvent;
import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.blocks.Board;
import com.creations.mvvm.models.blocks.Row;
import com.creations.mvvm.models.blocks.RowWrapper;
import com.creations.mvvm.ui.blocks.row.RowContract;
import com.creations.mvvm.ui.blocks.row.RowViewModel;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.example.application.utils.RecyclerUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class BoardViewModel extends RecyclerViewModel<Board> implements BoardContract.ViewModel<Board> {

    @NonNull
    private final RowViewModel.Factory mRowFactory;
    @NonNull
    private final BoardAdapter<RowContract.ViewModel, CardBlocksRowBinding> adapter = new BoardAdapter<>(viewModel -> {

    }, R.layout.card_blocks_row);

    @NonNull
    private final MutableLiveData<Integer> mGridColor = new MutableLiveData<>(R.color.colorPrimary);

    @NonNull
    private final LiveEvent.Mutable<RowWrapper> mAddCellEvent = new LiveEvent.Mutable<>();

    @NonNull
    private final LiveRunnable.Mutable mAddRowEvent = new LiveRunnable.Mutable();

    public BoardViewModel(@NonNull final Application application,
                          @NonNull final RowViewModel.Factory rowFactory,
                          @NonNull final Board board) {
        super(application, board);
        mRowFactory = Preconditions.requiresNonNull(rowFactory, "Factory");
        setProps(board);
        setLayoutType(RecyclerUtils.LayoutType.LINEAR_VERTICAL);
        setTopColor(R.color.colorPrimary);
    }

    @Override
    public void setProps(@NonNull final Board board) {
        super.setProps(board);
        adapter.clearItems();
        for (int i=0;i<board.getRows().size();i++) {
            RowViewModel viewModel = mRowFactory.create();
            Row rowInfo = board.getRows().get(i);
            viewModel.setProps(rowInfo);
            int finalI = i;
            viewModel.getAddEvent().observeForever(sentinel -> {
                mAddCellEvent.postEvent(new RowWrapper(rowInfo, finalI));
            });
            adapter.addItem(viewModel);
        }
    }

    @NonNull
    @Override
    public LiveRunnable.Mutable getAddRowEvent() {
        return mAddRowEvent;
    }

    @NonNull
    @Override
    public LiveEvent.Mutable<RowWrapper> getAddCellEvent() {
        return mAddCellEvent;
    }

    @NonNull
    @Override
    public LiveData<Integer> getGridColor() {
        return mGridColor;
    }

    @NonNull
    @Override
    public BoardAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void addRow(@NonNull Row props) {
        RowViewModel viewModel = mRowFactory.create();
        viewModel.setProps(props);
        adapter.addItem(viewModel);
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
