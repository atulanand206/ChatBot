package com.creations.mvvm.ui.blocks.row;

import android.app.Application;
import android.view.View;

import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.databinding.CardBlocksItemBinding;
import com.creations.mvvm.models.blocks.Cell;
import com.creations.mvvm.models.blocks.Row;
import com.creations.mvvm.ui.blocks.CellContract;
import com.creations.mvvm.ui.blocks.CellViewModel;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;

import androidx.annotation.NonNull;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class RowViewModel extends RecyclerViewModel implements RowContract.ViewModel {

    @NonNull
    private Row mRowProps;
    @NonNull
    private final CellViewModel.Factory mCellFactory;
    @NonNull
    private final RowAdapter<CellContract.ViewModel, CardBlocksItemBinding> adapter = new RowAdapter<>(viewModel -> {

    }, R.layout.card_blocks_item);

    public RowViewModel(@NonNull final Application application,
                        @NonNull final CellViewModel.Factory cellFactory,
                        @NonNull final Row rowInfo) {
        super(application);
        mCellFactory = Preconditions.requiresNonNull(cellFactory, "CellFactory");
        setData(rowInfo);
    }

    @Override
    public void setData(@NonNull final Row rowInfo) {
        adapter.clearItems();
        mRowProps = Preconditions.requiresNonNull(rowInfo, "RowInfo");
        for (Cell cell : rowInfo.getCells()) {
            CellViewModel cellViewModel = mCellFactory.create();
            cellViewModel.setData(cell);
            adapter.addItem(cellViewModel);
        }
        setVisibility(View.VISIBLE);
    }

    @NonNull
    @Override
    public RowAdapter getAdapter() {
        return adapter;
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
