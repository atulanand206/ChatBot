package com.creations.mvvm.ui.contact;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.databinding.CardContactBinding;
import com.creations.mvvm.ui.contact.ContactContract.ViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.RecyclerViewHolder> {

    private MutableLiveData<Boolean> mProceedError = new MutableLiveData<>();

    @NonNull
    private List<ViewModel> mViewModels;

    public ContactAdapter(@NonNull final List<ViewModel> viewModels) {
        mViewModels = Preconditions.requiresNonNull(viewModels, "ViewModels");
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, final int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(
                Preconditions.requiresNonNull(parent, "ViewGroup").getContext());
        CardContactBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, parent, false);

        return new RecyclerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewHolder holder, final int position) {
        Preconditions.requiresNonNull(holder, "Holder").bind(mViewModels.get(position));
        mViewModels.get(position).getItemDeleteEvent().listen(holder,
                () -> deleteContactItem(holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        return mViewModels.size();
    }

    @Override
    public int getItemViewType(final int position) {
        return R.layout.card_contact;
    }

    @NonNull
    public List<ViewModel> getViewModels() {
        return mViewModels;
    }

    public LiveData<Boolean> getProceedError() {
        return mProceedError;
    }

    public boolean hasError() {
        for (ViewModel viewModel : mViewModels) {
            Boolean error = viewModel.hasError().getValue();
            if (error == null || error) {
                mProceedError.postValue(true);
                return true;
            }
        }
        mProceedError.postValue(false);
        return false;
    }

    public void deleteContactItem(final int position) {
        if (position < 0 || position >= mViewModels.size()) {
            return;
        }
        mViewModels.remove(position);
        notifyItemRemoved(position);
        for (int i=position; i < mViewModels.size(); i++) {
            mViewModels.get(i).setPosition(i);
        }
    }

    public void addContactItem(@NonNull final ViewModel contactViewModel) {
        Preconditions.requiresNonNull(contactViewModel, "ContactViewModel");
        mViewModels.add(contactViewModel);
        notifyItemInserted(mViewModels.size() - 1);
    }

    public void clearItems() {
        mViewModels.clear();
        notifyDataSetChanged();
    }

    @Override
    public void onViewRecycled(@NonNull final RecyclerViewHolder holder) {
        Preconditions.requiresNonNull(holder, "RecyclerViewHolder").onViewRecycled();
    }

    @Override
    public void onViewAttachedToWindow(@NonNull final RecyclerViewHolder holder) {
        Preconditions.requiresNonNull(holder, "RecyclerViewHolder").onViewAttachedToWindow();
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull final RecyclerViewHolder holder) {
        Preconditions.requiresNonNull(holder, "RecyclerViewHolder").onViewDetachedFromWindow();
    }

    public void setDisabled(final boolean disabled) {
        for (ViewModel viewModel : mViewModels) {
            viewModel.setDisabled(disabled);
        }
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder implements LifecycleOwner {

        @NonNull
        private LifecycleRegistry mRegistry;
        @NonNull
        private final CardContactBinding mBinding;

        public RecyclerViewHolder(@NonNull final CardContactBinding binding) {
            super(binding.getRoot());
            mBinding = Preconditions.requiresNonNull(binding, "Binding");
            mRegistry = new LifecycleRegistry(this);
            mRegistry.setCurrentState(Lifecycle.State.INITIALIZED);
            mBinding.setLifecycleOwner(null);
            mBinding.executePendingBindings();
        }

        void bind(@NonNull final ViewModel viewModel) {
            mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_CREATE);
            mRegistry.handleLifecycleEvent(Lifecycle.Event.ON_START);
            viewModel.setPosition(getAdapterPosition());
            mBinding.setViewmodel(Preconditions.requiresNonNull(viewModel, "ViewModel"));
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
}
