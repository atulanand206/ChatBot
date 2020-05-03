package com.creations.mvvm.ui.recycler;

import com.creations.condition.Preconditions;
import com.example.application.utils.RecyclerUtils;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

public class LoopingRecyclerAdapter<T extends IRecyclerViewModel, E extends ViewDataBinding> extends RecyclerAdapter<T, E> {

    public static final int ROW_CELL_COUNT = 10000;
    @NonNull
    private RecyclerUtils.LayoutType mLayoutType;

    public LoopingRecyclerAdapter(@NonNull RecyclerListener<T> listener, int layoutResId,
                                  @NonNull RecyclerUtils.LayoutType layoutType) {
        super(listener, layoutResId);
        mLayoutType = Preconditions.requiresNonNull(layoutType, "LayoutType");
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder<T, E> holder, int position) {
        if (isLoop() && !mViewModels.isEmpty()) {
            position = position % mViewModels.size();
            Preconditions.requiresNonNull(holder, "Holder").bind(mViewModels.get(position));
            mViewModels.get(position).getClickedEvent().listen(holder, () -> onClick(holder.getAbsoluteAdapterPosition()));
        } else {
            super.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        if (mViewModels.isEmpty())
            return 1;
        return isLoop() ? ROW_CELL_COUNT : mViewModels.size();
    }

    private boolean isLoop() {
        return mLayoutType.equals(RecyclerUtils.LayoutType.LOOP_HORIZONTAL) || mLayoutType.equals(RecyclerUtils.LayoutType.LOOP_VERTICAL);
    }

    public void setLayoutType(@NonNull RecyclerUtils.LayoutType layoutType) {
        this.mLayoutType = layoutType;
    }
}
