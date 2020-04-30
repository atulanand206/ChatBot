package com.creations.mvvm.ui.blocks.board;

import android.app.Application;

import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.databinding.CardBlocksRowBinding;
import com.creations.mvvm.live.LiveEvent;
import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.array.Arr;
import com.creations.mvvm.models.blocks.Board;
import com.creations.mvvm.models.blocks.Cell;
import com.creations.mvvm.models.blocks.Row;
import com.creations.mvvm.models.blocks.RowWrapper;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.blocks.row.RowContract;
import com.creations.mvvm.ui.blocks.row.RowViewModel;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.creations.tools.utils.JsonConvertor;
import com.example.application.utils.RecyclerUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.COLOR_NORMAL;

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
    private final MutableLiveData<Integer> mGridColor = new MutableLiveData<>(COLOR_NORMAL);

    @NonNull
    private final LiveEvent.Mutable<RowWrapper> mAddCellEvent = new LiveEvent.Mutable<>();

    @NonNull
    private final LiveRunnable.Mutable mAddRowEvent = new LiveRunnable.Mutable();

    @NonNull
    private final LiveEvent.Mutable<Props> mAddWordEvent = new LiveEvent.Mutable<>();

    @NonNull
    private final Arr mCells = new Arr();

    @NonNull
    private final JsonConvertor mJsonConvertor;

    public BoardViewModel(@NonNull final Application application,
                          @NonNull final RowViewModel.Factory rowFactory,
                          @NonNull final JsonConvertor jsonConvertor,
                          @NonNull final Board board) {
        super(application, board);
        mRowFactory = Preconditions.requiresNonNull(rowFactory, "Factory");
        mJsonConvertor = Preconditions.requiresNonNull(jsonConvertor, "JsonConvertor");
        setProps(board);
        setLayoutType(RecyclerUtils.LayoutType.LINEAR_VERTICAL);
        setTopColor(COLOR_NORMAL);
        setBackgroundColor(COLOR_NORMAL);
    }

    @Override
    public void setProps(@NonNull final Board board) {
        super.setProps(board);
        setBackgroundColor(board.getColorResId());
        adapter.clearItems();
        List<Row> rows = board.getRows();
        for (int i = 0; i< rows.size(); i++) {
            RowViewModel viewModel = mRowFactory.create();
            mContextCallback.addSource(viewModel.getContextCallback());
            Row rowInfo = rows.get(i);
            viewModel.setProps(rowInfo);
            int finalI1 = i;
            viewModel.getClickEvent().observeForever(o -> {
                if (o instanceof Row) {
                    int clickedIndex = ((Row) o).getClickedIndex();
                    clickedIndex = clickedIndex % rowInfo.getCells().size();
                    Cell cell = rowInfo.getCells().get(clickedIndex);
                    mCells.add(finalI1, clickedIndex, cell);
                    mCells.setCurrentIndex(finalI1);
                    postCharacter(rows, finalI1, clickedIndex);
                }
            });
            adapter.addItem(viewModel);
        }
        adapter.notifyDataSetChanged();
    }

    public void notifyDataSetChanged() {
        Board board = getProps();
        board = board.refresh(mCells);
        setProps(board);
    }

    private void postCharacter(List<Row> rows, int finalI1, int clickedIndex) {
        BoardViewModel.this.getClickEvent().postEvent(rows.get(finalI1).getCells().get(clickedIndex));
    }

    @Override
    public void valid() {
        mCells.setItems(mCells.valid().getItems());
        notifyDataSetChanged();
    }

    @Override
    public void invalid() {
        mCells.setItems(mCells.invalid().getItems());
        notifyDataSetChanged();
    }

    @Override
    public void clear() {
        mCells.clear();
        notifyDataSetChanged();
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
    public LiveEvent.Mutable<Props> getAddWordEvent() {
        return mAddWordEvent;
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
        private final JsonConvertor mJsonConvertor;

        @NonNull
        private final Board mProps;


        public Factory(@NonNull final Application application,
                       @NonNull final RowViewModel.Factory rowFactory,
                       @NonNull final JsonConvertor jsonConvertor,
                       @NonNull final Board props) {
            super(BoardViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
            mRowFactory = Preconditions.requiresNonNull(rowFactory, "RowFactory");
            mJsonConvertor = Preconditions.requiresNonNull(jsonConvertor, "JsonConvertor");
        }

        @NonNull
        @Override
        public BoardViewModel create() {
            return new BoardViewModel(mApplication, mRowFactory, mJsonConvertor, mProps);
        }
    }
}
