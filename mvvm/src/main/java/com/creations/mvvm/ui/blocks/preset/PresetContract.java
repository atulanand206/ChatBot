package com.creations.mvvm.ui.blocks.preset;

import com.creations.mvvm.databinding.CardBlocksBoardItemBinding;
import com.creations.mvvm.models.blocks.Preset;
import com.creations.mvvm.ui.blocks.item.ItemContract;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

import androidx.annotation.NonNull;

public interface PresetContract {

    interface ViewModel<T extends Preset> extends IRecyclerViewModel<T> {

        @NonNull
        PresetAdapter<ItemContract.ViewModel, CardBlocksBoardItemBinding> getAdapter();
    }

}
