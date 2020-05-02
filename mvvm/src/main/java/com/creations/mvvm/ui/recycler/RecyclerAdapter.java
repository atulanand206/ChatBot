package com.creations.mvvm.ui.recycler;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.creations.condition.Preconditions;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerAdapter<T extends IRecyclerViewModel, E extends ViewDataBinding> extends RecyclerView.Adapter<RecyclerViewHolder<T, E>> {

    @NonNull
    protected List<T> mViewModels;
    @NonNull
    private RecyclerListener<T> mListener;
    private int mLayoutResId;

    public RecyclerAdapter(@NonNull final RecyclerListener<T> listener,
                           final int layoutResId) {
        mViewModels = new ArrayList<>();
        mListener = Preconditions.requiresNonNull(listener, "Listener");
        mLayoutResId = layoutResId;
    }

    @NonNull
    @Override
    public RecyclerViewHolder<T, E> onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(
                Preconditions.requiresNonNull(parent, "ViewGroup").getContext());
        E binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);

        return new RecyclerViewHolder<>(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder<T, E> holder, final int position) {
        Preconditions.requiresNonNull(holder, "Holder").bind(mViewModels.get(position));
        mViewModels.get(position).getClickedEvent().listen(holder, () -> onClick(holder.getAbsoluteAdapterPosition()));
    }

    public void onClick(final int adapterPosition) {
        mViewModels.get(adapterPosition).onRecyclerItemClick();
        mListener.onItemClick(mViewModels.get(adapterPosition));
    }

    @Override
    public int getItemCount() {
        return mViewModels.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return mLayoutResId;
    }

    @NonNull
    public List<T> getViewModels() {
        return mViewModels;
    }

    public void setViewModels(@NonNull final List<T> viewModels) {
        mViewModels.clear();
        mViewModels.addAll(viewModels);
        notifyDataSetChanged();
    }

    public void deleteItem(final int position) {
        if (position < 0 || position >= mViewModels.size()) {
            return;
        }
        mViewModels.remove(position);
        notifyItemRemoved(position);
        for (int i=position; i < mViewModels.size(); i++) {
            mViewModels.get(i).setPosition(i);
        }
    }

    public void addItem(@NonNull final T viewModel) {
        Preconditions.requiresNonNull(viewModel, "ViewModel");
        mViewModels.add(viewModel);
        notifyItemInserted(mViewModels.size() - 1);
    }

    public void insertItem(@NonNull final T viewModel) {
        Preconditions.requiresNonNull(viewModel, "ViewModel");
        int pos = mViewModels.size()-1;
        mViewModels.add(pos, viewModel);
        notifyItemInserted(mViewModels.size() - 1);
    }

    public void clearItems() {
        mViewModels.clear();
        notifyDataSetChanged();
    }

}
