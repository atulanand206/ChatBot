package com.creations.blocks.ui.preset;

import android.app.Application;
import android.view.View;

import com.creations.blocks.api.IAPIBlocks;
import com.creations.blocks.models.Board;
import com.creations.blocks.models.Preset;
import com.creations.blocks.ui.add.AddContract;
import com.creations.blocks.ui.item.ItemContract;
import com.creations.blocks.ui.item.ItemViewModel;
import com.creations.condition.Preconditions;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.example.application.utils.RecyclerUtils;
import com.example.blocks.R;
import com.example.blocks.databinding.CardBlocksBoardItemBinding;

import androidx.annotation.NonNull;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class PresetViewModel extends RecyclerViewModel<Preset> implements PresetContract.ViewModel<Preset> {

    @NonNull
    private final IAPIBlocks mApiChat;
    @NonNull
    private final ItemViewModel.Factory mFactory;
    @NonNull
    private final PresetAdapter<ItemContract.ViewModel, CardBlocksBoardItemBinding> adapter = new PresetAdapter<>(viewModel -> {

    }, R.layout.card_blocks_board_item);


    public PresetViewModel(@NonNull final Application application,
                           @NonNull final IAPIBlocks apiChat,
                           @NonNull final ItemViewModel.Factory itemFactory,
                           @NonNull final Preset props) {
        super(application, props);
        mFactory = itemFactory;
        mApiChat = apiChat;
        setVisibility(View.VISIBLE);
        setLayoutType(RecyclerUtils.LayoutType.LINEAR_VERTICAL);
        setBackgroundColor(props.getColorResId());
    }

    @Override
    public void setProps(@NonNull Preset props) {
        super.setProps(props);
        for (Board board : props.getBoard()) {
            ItemViewModel itemViewModel = mFactory.create();
            itemViewModel.setProps(board);
            itemViewModel.getClickEvent().observeForever(o -> {
                if (o instanceof Board)
                    PresetViewModel.this.getClickEvent().postEvent(board);
            });
            adapter.addItem(itemViewModel);
        }
    }

    @NonNull
    @Override
    public PresetAdapter<ItemContract.ViewModel, CardBlocksBoardItemBinding> getAdapter() {
        return adapter;
    }

    @Override
    public void onClick(@NonNull Object object) {
        if (object instanceof Integer) {
            if (object.equals(AddContract.ViewModel.CLICK_TO_HOME))
                PresetViewModel.this.getClickEvent().postEvent(AddContract.ViewModel.CLICK_TO_HOME);
        }
    }

    public static class Factory extends MVVMViewModel.Factory<PresetViewModel> {

        @NonNull
        private final IAPIBlocks mApiChat;
        @NonNull
        private final Preset mProps;
        @NonNull
        private final ItemViewModel.Factory mItemFactory;

        public Factory(@NonNull final Application application,
                       @NonNull final IAPIBlocks apiChat,
                       @NonNull final ItemViewModel.Factory itemFactory,
                       @NonNull final Preset props) {
            super(PresetViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
            mApiChat = Preconditions.requiresNonNull(apiChat, "ApiChat");
            mItemFactory = Preconditions.requiresNonNull(itemFactory, "ItemFactory");
        }

        @NonNull
        @Override
        public PresetViewModel create() {
            return new PresetViewModel(mApplication, mApiChat, mItemFactory, mProps);
        }
    }
}
