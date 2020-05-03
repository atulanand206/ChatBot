package com.creations.blocks.ui.row;

import android.app.Application;
import android.view.View;
import android.widget.Toast;

import com.creations.blocks.models.Cell;
import com.creations.blocks.models.Row;
import com.creations.blocks.ui.add.AddContract;
import com.creations.blocks.ui.cell.CellContract;
import com.creations.blocks.ui.cell.CellViewModel;
import com.creations.blocks.utils.BoardUtils;
import com.creations.condition.Preconditions;
import com.creations.mvvm.live.LiveEvent;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.example.application.messages.MessageType;
import com.example.blocks.R;
import com.example.blocks.databinding.CardBlocksItemBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class RowViewModel extends RecyclerViewModel<Row> implements RowContract.ViewModel<Row> {

    @NonNull
    private final CellViewModel.Factory mCellFactory;
    @NonNull
    private final RowAdapter<CellContract.ViewModel, CardBlocksItemBinding> adapter = new RowAdapter<>(viewModel -> {
        mMessageManager.showToast("character.getValue()", MessageType.SUCCESS, Toast.LENGTH_LONG);
    }, R.layout.card_blocks_item);

    @NonNull
    private final LiveEvent.Mutable<Row> mAddEvent = new LiveEvent.Mutable<>();
    private CellViewModel addViewModel;

    public RowViewModel(@NonNull final Application application,
                        @NonNull final CellViewModel.Factory cellFactory,
                        @NonNull final Row rowInfo) {
        super(application, rowInfo);
        mCellFactory = Preconditions.requiresNonNull(cellFactory, "CellFactory");
        setRows(rowInfo);
    }

    @Override
    public void setRows(@NonNull final Row rowInfo) {
        if (rowInfo.getCells().isEmpty())
            return;
        addViewModel = mCellFactory.create();
        adapter.setLayoutType(rowInfo.getLayoutType());
        setLayoutType(rowInfo.getLayoutType());
        addViewModel.setProps(BoardUtils.addCell());
        List<CellContract.ViewModel> viewModels = new ArrayList<>();
        for (int i=0;i<rowInfo.getCells().size();i++) {
            Cell cell = rowInfo.getCells().get(i);
            cell.setClickable(rowInfo.isClickable());
            CellViewModel cellViewModel = mCellFactory.create();
            mContextCallback.addSource(cellViewModel.getContextCallback());
            cellViewModel.setProps(cell);
            viewModels.add(cellViewModel);
        }
        adapter.setViewModels(viewModels);
        setVisibility(View.VISIBLE);
        setEditable(rowInfo.isAddVisibility());
    }

    @Override
    public void setProps(@NonNull final Row rowInfo) {
        if (rowInfo.getCells().isEmpty())
            return;
        setBackgroundColor(rowInfo.getColorResId());
        setText(rowInfo.getWord());
        setTextColorResId(rowInfo.getColorResId());
        setTextSize(AddContract.ViewModel.COLOR_ADD_CLEAR);
        List<CellContract.ViewModel> viewModels = adapter.getViewModels();
        if (viewModels.size()==rowInfo.getCells().size()) {
            for (int i = 0; i < viewModels.size(); i++) {
                int finalI = i;
                CellViewModel cellViewModel = (CellViewModel) viewModels.get(i);
                cellViewModel.setProps(rowInfo.getCells().get(i));
                cellViewModel.getRefreshEvent().observeForever(sentinel -> {
                    adapter.notifyDataSetChanged();
                });
                cellViewModel.getClickEvent().observeForever(o -> {
                    if (o instanceof Cell) {
                        if (rowInfo.isClickable()) {
                            getProps().setClickedIndex(finalI);
                            RowViewModel.this.getClickEvent().postEvent(getProps());
                            RowViewModel.this.getRefreshEvent().postEvent();
                        }
                    }
                });
                notifyDataSetChanged();
            }
        }

    }

    @Override
    public void setBackgroundColor(int backgroundColorResId) {
        super.setBackgroundColor(backgroundColorResId);
        List<CellContract.ViewModel> viewModels = adapter.getViewModels();
        for (CellContract.ViewModel viewModel : viewModels)
            viewModel.setBackgroundColor(backgroundColorResId);
    }

    @Override
    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        List<CellContract.ViewModel> viewModels = adapter.getViewModels();
        for (CellContract.ViewModel viewModel : viewModels) {
            viewModel.setClickable(clickable);
        }
    }

    @Override
    public void shuffle(final boolean shuffle) {
        super.shuffle(shuffle);
        List<CellContract.ViewModel> viewModels = adapter.getViewModels();
        for (CellContract.ViewModel viewModel : viewModels)
            viewModel.shuffle(shuffle);
    }

    @NonNull
    @Override
    public LiveEvent.Mutable getAddEvent() {
        return mAddEvent;
    }

    @NonNull
    @Override
    public RowAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void notifyDataSetChanged() {
        adapter.notifyDataSetChanged();
//        setLayoutType(getLayoutType());
    }

    public static class Factory extends MVVMViewModel.Factory<RowViewModel> {

        @NonNull
        private final CellViewModel.Factory mCellFactory;

        @NonNull
        private final Row mRowInfo;


        public Factory(@NonNull final Application application,
                       @NonNull final CellViewModel.Factory cellFactory,
                       @NonNull final Row rowInfo) {
            super(RowViewModel.class, application);
            mRowInfo = Preconditions.requiresNonNull(rowInfo, "RowInfo");
            mCellFactory = Preconditions.requiresNonNull(cellFactory, "CellFactory");
        }

        @NonNull
        @Override
        public RowViewModel create() {
            return new RowViewModel(mApplication, mCellFactory, mRowInfo);
        }
    }
}
