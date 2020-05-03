package com.creations.blocks.ui.preset;

import com.creations.blocks.models.Preset;
import com.creations.blocks.ui.item.ItemContract;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;
import com.example.blocks.databinding.CardBlocksBoardItemBinding;

import androidx.annotation.NonNull;

public interface PresetContract {

    interface ViewModel<T extends Preset> extends IRecyclerViewModel<T> {

        @NonNull
        PresetAdapter<ItemContract.ViewModel, CardBlocksBoardItemBinding> getAdapter();
    }

}
