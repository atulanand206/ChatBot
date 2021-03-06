package com.creations.blocks.ui.row;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.creations.blocks.ui.cell.CellContract;
import com.creations.condition.Preconditions;
import com.creations.mvvm.ui.recycler.LoopingRecyclerAdapter;
import com.creations.mvvm.ui.recycler.RecyclerListener;
import com.creations.mvvm.ui.recycler.RecyclerViewHolder;
import com.example.application.utils.RecyclerUtils;
import com.example.blocks.databinding.CardBlocksItemBinding;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

public class RowAdapter<T extends CellContract.ViewModel, E extends CardBlocksItemBinding> extends LoopingRecyclerAdapter<T, E> {

    public RowAdapter(@NonNull final RecyclerListener<T> listener, final int layoutResId) {
        super(listener, layoutResId, RecyclerUtils.LayoutType.LOOP_HORIZONTAL);
    }

    @NonNull
    @Override
    public ViewHolder<T, E> onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(
                Preconditions.requiresNonNull(parent, "ViewGroup").getContext());
        E binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        return new ViewHolder<T, E>(binding);
    }

    public static class ViewHolder<T extends CellContract.ViewModel, E extends CardBlocksItemBinding> extends RecyclerViewHolder<T, E> {

        ViewHolder(@NonNull final E binding) {
            super(binding);
        }

        @Override
        public void bind(@NonNull T viewModel) {
            super.bind(viewModel);
            viewModel.setPosition(getAbsoluteAdapterPosition());
            mBinding.setViewmodel(Preconditions.requiresNonNull(viewModel, "ViewModel"));
        }

        @Override
        public void onViewAttachedToWindow() {
            super.onViewAttachedToWindow();
        }

        @Override
        public void onViewDetachedFromWindow() {
            super.onViewDetachedFromWindow();
        }

        @Override
        public void onViewRecycled() {
            super.onViewRecycled();
        }
    }
}
