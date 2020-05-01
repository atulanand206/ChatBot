package com.creations.mvvm.ui.blocks.preset;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.creations.condition.Preconditions;
import com.creations.mvvm.databinding.CardBlocksBoardItemBinding;
import com.creations.mvvm.ui.blocks.item.ItemContract;
import com.creations.mvvm.ui.recycler.RecyclerAdapter;
import com.creations.mvvm.ui.recycler.RecyclerListener;
import com.creations.mvvm.ui.recycler.RecyclerViewHolder;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

public class PresetAdapter<T extends ItemContract.ViewModel, E extends CardBlocksBoardItemBinding> extends RecyclerAdapter<T, E> {

    public PresetAdapter(@NonNull final RecyclerListener<T> listener, final int layoutResId) {
        super(listener, layoutResId);
    }

    @NonNull
    @Override
    public ViewHolder<T, E> onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(
                Preconditions.requiresNonNull(parent, "ViewGroup").getContext());
        E binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);
        return new ViewHolder<T, E>(binding);
    }

    public static class ViewHolder<T extends ItemContract.ViewModel, E extends CardBlocksBoardItemBinding> extends RecyclerViewHolder<T, E> {

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
