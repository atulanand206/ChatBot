package com.creations.mvvm.ui.blocks.board;

import android.app.Application;
import android.view.View;

import com.creations.blogger.callback.ObjectResponseCallback;
import com.creations.blogger.model.APIResponseBody;
import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.constants.IAPIChat;
import com.creations.mvvm.databinding.CardBlocksRowBinding;
import com.creations.mvvm.live.LiveEvent;
import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.blocks.Board;
import com.creations.mvvm.models.blocks.Cell;
import com.creations.mvvm.models.blocks.Row;
import com.creations.mvvm.models.blocks.RowWrapper;
import com.creations.mvvm.models.blocks.Word;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.blocks.row.RowContract;
import com.creations.mvvm.ui.blocks.row.RowViewModel;
import com.creations.mvvm.ui.blocks.word.WordViewModel;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.example.application.utils.RecyclerUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.COLOR_ADD_ERROR;
import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.COLOR_ADD_GO;
import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.COLOR_NORMAL;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class BoardViewModel extends RecyclerViewModel<Board> implements BoardContract.ViewModel<Board> {

    @NonNull
    private final IAPIChat mApiChat;
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
                          @NonNull final IAPIChat apiChat,
                          @NonNull final Board board) {
        super(application, board);
        setVisibility(View.GONE);
        mApiChat = apiChat;
        mRowFactory = Preconditions.requiresNonNull(rowFactory, "Factory");
        mWordViewModel = wordFactory.create();
        mContextCallback.addSource(mWordViewModel.getContextCallback());
        setLayoutType(RecyclerUtils.LayoutType.LINEAR_VERTICAL);
        setTopColor(COLOR_NORMAL);
        setBackgroundColor(COLOR_NORMAL);
    }

    @Override
    public void setRows(@NonNull final Board board) {
        adapter.clearItems();
        List<Row> rows = board.getRows();
        for (int i = 0; i< rows.size(); i++) {
            RowViewModel viewModel;
                viewModel = mRowFactory.create();
            mContextCallback.addSource(viewModel.getContextCallback());
            Row rowInfo = rows.get(i);
            rowInfo.setLayoutType(RecyclerUtils.LayoutType.LOOP_HORIZONTAL);
            viewModel.setRows(rowInfo);
            int finalI1 = i;
            viewModel.getRefreshEvent().observeForever(sentinel -> {
//                setProps(getProps());
            });
            viewModel.getClickEvent().observeForever(o -> {
                if (o instanceof Row) {
                    int clickedIndex = ((Row) o).getClickedIndex();
                    clickedIndex = clickedIndex % rowInfo.getCells().size();
                    Cell cell = rowInfo.getCells().get(clickedIndex);
                    getProps().add(finalI1, clickedIndex, cell);
                    mWordViewModel.refresh(getProps().getSelections());
                    mWordViewModel.valid(new ObjectResponseCallback<Word>() {
                        @Override
                        public void onSuccess(@NonNull Word response) {
                            BoardViewModel.this.getAddWordEvent().postEvent(response);
                            setProps(board.valid());
                            setBackgroundColor(COLOR_ADD_GO);
                        }

                        @Override
                        public void onError(int statusCode, @NonNull String errorResponse, @NonNull APIResponseBody serializedErrorResponse, @Nullable Exception e) {
                            setProps(board.invalid());
                            setBackgroundColor(COLOR_ADD_ERROR);
                        }
                    });
                }
            });
            adapter.addItem(viewModel);
        }
        setProps(board);
    }

    @Override
    public void setProps(@NonNull final Board board) {
        super.setProps(board);
        setBackgroundColor(board.getColorResId());
        List<RowContract.ViewModel> viewModels = adapter.getViewModels();
        if (viewModels.size()==board.getRows().size()) {
            for (int i = 0; i < viewModels.size(); i++) {
                RowViewModel rowViewModel = (RowViewModel) viewModels.get(i);
                rowViewModel.setProps(board.getRows().get(i));
                rowViewModel.setLayoutType(RecyclerUtils.LayoutType.LOOP_HORIZONTAL);
                rowViewModel.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void clear() {
        setProps(getProps().clear());
    }

    @Override
    public void refresh() {
        getRefreshEvent().postEvent();
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

        @NonNull
        private final IAPIChat mApiChat;


        public Factory(@NonNull final Application application,
                       @NonNull final RowViewModel.Factory rowFactory,
                       @NonNull final WordViewModel.Factory wordFactory,
                       @NonNull final IAPIChat apiChat,
                       @NonNull final Board props) {
            super(BoardViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
            mRowFactory = Preconditions.requiresNonNull(rowFactory, "RowFactory");
            mWordFactory = Preconditions.requiresNonNull(wordFactory, "Factory");
            mApiChat = Preconditions.requiresNonNull(apiChat, "ApiChat");
        }

        @NonNull
        @Override
        public BoardViewModel create() {
            return new BoardViewModel(mApplication, mRowFactory, mWordFactory, mApiChat, mProps);
        }
    }
}
