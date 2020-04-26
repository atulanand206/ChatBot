package com.creations.mvvm.ui.recycler;

import com.creations.condition.Preconditions;
import com.creations.mvvm.viewmodel.IMVVMViewModel;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewHolder<T extends IMVVMViewModel, E extends ViewDataBinding> extends RecyclerView.ViewHolder implements LifecycleOwner {

    @NonNull
    private LifecycleRegistry mRegistry;
    @NonNull
    protected final E mBinding;

    public RecyclerViewHolder(@NonNull E binding) {
        super(binding.getRoot());
        mBinding = Preconditions.requiresNonNull(binding, "Binding");
        mRegistry = new LifecycleRegistry(this);
        mRegistry.setCurrentState(Lifecycle.State.INITIALIZED);
        mBinding.setLifecycleOwner(null);
        mBinding.executePendingBindings();
    }

    public void bind(@NonNull final T viewModel) {
        mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
        mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
        mBinding.executePendingBindings();
    }

    public void onViewAttachedToWindow() {
        mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        mBinding.setLifecycleOwner(this);
        mBinding.executePendingBindings();
    }

    public void onViewDetachedFromWindow() {
        mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_PAUSE);
        mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_STOP);
        mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_DESTROY);
    }

    public void onViewRecycled() {
        mBinding.setLifecycleOwner(null);
        mBinding.executePendingBindings();
        mRegistry = new LifecycleRegistry(this);
        mRegistry.setCurrentState(Lifecycle.State.INITIALIZED);
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mRegistry;
    }
}
