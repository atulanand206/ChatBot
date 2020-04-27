package com.creations.mvvm.ui.recycler;

public interface RecyclerListener<T extends IRecyclerViewModel> {

    void onItemClick(final T viewModel);
}
