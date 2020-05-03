package com.creations.blocks.ui.item;

import com.creations.blocks.ui.options.OptionsAdapter;
import com.creations.blocks.ui.options.OptionsContract;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;
import com.example.blocks.databinding.CardBlocksBoardWordListBinding;

import androidx.annotation.NonNull;

public interface ItemContract {

    interface ViewModel<T extends Props> extends IRecyclerViewModel<T> {

        @NonNull
        OptionsAdapter<OptionsContract.ViewModel, CardBlocksBoardWordListBinding> getAdapter();
    }

}
