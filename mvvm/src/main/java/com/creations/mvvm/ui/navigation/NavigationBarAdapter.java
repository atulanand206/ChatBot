package com.creations.mvvm.ui.navigation;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.creations.condition.Preconditions;
import com.creations.mvvm.BR;
import com.creations.mvvm.models.navigation.NavigationItem;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public class NavigationBarAdapter extends RecyclerView.Adapter<NavigationBarAdapter.RecyclerViewHolder>{

    private int mLayoutId;

    @NonNull
    private NavigationBarContract.ViewModel mViewModel;

    public NavigationBarAdapter(final int layoutId,
                                @NonNull final NavigationBarContract.ViewModel navigationBarViewModel) {
        mLayoutId = layoutId;
        mViewModel = Preconditions.requiresNonNull(navigationBarViewModel, "NavigationBarViewModel");
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(
                Preconditions.requiresNonNull(parent, "ViewGroup").getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);

        return new RecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {
        Preconditions.requiresNonNull(holder, "Holder").bind(mViewModel, position);
    }

    @Override
    public int getItemCount() {
        List<NavigationItem> value = mViewModel.getProps().getItems();
        return value != null ? value.size() : 0;
    }

    @Override
    public int getItemViewType(final int position) {
        return mLayoutId;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        final ViewDataBinding mBinding;

        public RecyclerViewHolder(@NonNull final ViewDataBinding binding) {
            super(binding.getRoot());
            mBinding = Preconditions.requiresNonNull(binding, "Binding");
        }

        void bind(@NonNull final NavigationBarContract.ViewModel viewModel,
                  @NonNull final Integer position) {
            mBinding.setVariable(BR.viewmodel, Preconditions.requiresNonNull(viewModel, "ViewModel"));
            mBinding.setVariable(BR.position, Preconditions.requiresNonNull(position, "Position"));
            mBinding.executePendingBindings();
        }
    }
}
