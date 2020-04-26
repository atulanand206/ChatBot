package com.creations.mvvm.ui.recycler;

import com.creations.mvvm.viewmodel.IMVVMViewModel;

public interface RecyclerListener<T extends IMVVMViewModel> {

    void onItemClick(final T viewModel);
}
