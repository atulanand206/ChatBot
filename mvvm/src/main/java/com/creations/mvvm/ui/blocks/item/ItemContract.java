package com.creations.mvvm.ui.blocks.item;

import android.text.SpannableString;

import com.creations.mvvm.databinding.CardBlocksBoardWordListBinding;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.blocks.options.OptionsAdapter;
import com.creations.mvvm.ui.blocks.options.OptionsContract;
import com.creations.mvvm.ui.recycler.IRecyclerViewModel;

import androidx.annotation.NonNull;

public interface ItemContract {

    interface ViewModel<T extends Props> extends IRecyclerViewModel<T> {

        @NonNull
        MutableLiveData<SpannableString> getString();

        @NonNull
        OptionsAdapter<OptionsContract.ViewModel, CardBlocksBoardWordListBinding> getAdapter();
    }

}
