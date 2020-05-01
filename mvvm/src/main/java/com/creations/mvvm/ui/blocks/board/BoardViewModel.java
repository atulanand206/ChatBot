package com.creations.mvvm.ui.blocks.board;

import android.app.Application;

import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.databinding.CardBlocksRowBinding;
import com.creations.mvvm.live.LiveEvent;
import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.blocks.Board;
import com.creations.mvvm.models.blocks.Cell;
import com.creations.mvvm.models.blocks.Row;
import com.creations.mvvm.models.blocks.RowWrapper;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.blocks.row.RowContract;
import com.creations.mvvm.ui.blocks.row.RowViewModel;
import com.creations.mvvm.ui.blocks.word.WordViewModel;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.example.application.utils.RecyclerUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.COLOR_ADD_ERROR;
import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.COLOR_ADD_GO;
import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.COLOR_NORMAL;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class BoardViewModel extends RecyclerViewModel<Board> implements BoardContract.ViewModel<Board> {

    @NonNull
    private final RowViewModel.Factory mRowFactory;

    @NonNull
    private final WordViewModel mWordViewModel;
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

    public BoardViewModel(@NonNull final Application application,
                          @NonNull final RowViewModel.Factory rowFactory,
                          @NonNull final WordViewModel.Factory wordFactory,
                          @NonNull final Board board) {
        super(application, board);
        mRowFactory = Preconditions.requiresNonNull(rowFactory, "Factory");
        mWordViewModel = wordFactory.create();
        mContextCallback.addSource(mWordViewModel.getContextCallback());
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
                    getProps().add(finalI1, clickedIndex, cell);
                    mWordViewModel.refresh(getProps().getSelections());
                    if (mWordViewModel.valid()) {
                        setProps(board.valid());
                        setBackgroundColor(COLOR_ADD_GO);
                    } else {
                        setProps(board.invalid());
                        setBackgroundColor(COLOR_ADD_ERROR);
                    }
                }
            });
            adapter.addItem(viewModel);
        }
    }

    @Override
    public void clear() {
        setProps(getProps().clear());
    }

    @Override
    public String getWord() {
        return mWordViewModel.getWord();
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
    public WordViewModel getWordViewModel() {
        return mWordViewModel;
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
        private final WordViewModel.Factory mWordFactory;

        @NonNull
        private final Board mProps;


        public Factory(@NonNull final Application application,
                       @NonNull final RowViewModel.Factory rowFactory,
                       @NonNull final WordViewModel.Factory wordFactory,
                       @NonNull final Board props) {
            super(BoardViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
            mRowFactory = Preconditions.requiresNonNull(rowFactory, "RowFactory");
            mWordFactory = Preconditions.requiresNonNull(wordFactory, "Factory");
        }

        @NonNull
        @Override
        public BoardViewModel create() {
            return new BoardViewModel(mApplication, mRowFactory, mWordFactory, mProps);
        }
    }
}
