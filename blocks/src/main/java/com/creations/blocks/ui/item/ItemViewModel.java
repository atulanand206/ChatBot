package com.creations.blocks.ui.item;

import android.app.Application;
import android.view.View;

import com.creations.blocks.models.Board;
import com.creations.blocks.models.Row;
import com.creations.blocks.ui.options.OptionsAdapter;
import com.creations.blocks.ui.options.OptionsContract;
import com.creations.blocks.ui.options.OptionsViewModel;
import com.creations.condition.Preconditions;
import com.creations.mvvm.ui.recycler.RecyclerListener;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.example.application.utils.RecyclerUtils;
import com.example.blocks.R;
import com.example.blocks.databinding.CardBlocksBoardWordListBinding;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

import static com.creations.blocks.ui.add.AddContract.ViewModel.COLOR_WHITE;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class ItemViewModel extends RecyclerViewModel<Board> implements ItemContract.ViewModel<Board> {

    @NonNull
    private final OptionsViewModel.Factory mFactory;

    @NonNull
    private final OptionsAdapter<OptionsContract.ViewModel, CardBlocksBoardWordListBinding> adapter = new OptionsAdapter<>(new RecyclerListener<OptionsContract.ViewModel>() {
        @Override
        public void onItemClick(OptionsContract.ViewModel viewModel) {

        }
    }, R.layout.card_blocks_board_word_list);

    public ItemViewModel(@NonNull final Application application,
                         @NonNull final OptionsViewModel.Factory factory,
                         @NonNull final Board props) {
        super(application, props);
        mFactory = factory;
        setLayoutType(RecyclerUtils.LayoutType.LINEAR_VERTICAL);
        setVisibility(View.VISIBLE);
        setProps(props);
    }

    @Override
    public void setProps(@NonNull Board props) {
        super.setProps(props);
        setTitle(props.getName());
        List<OptionsContract.ViewModel> viewModels = new ArrayList<>();
        for (Row row : props.getRows()) {
            OptionsViewModel optionsViewModel = mFactory.create();
            optionsViewModel.setText(row.getWord().replace("\"",""));
            viewModels.add(optionsViewModel);
        }
        adapter.setViewModels(viewModels);
        setSubHeader(String.valueOf(props.getId()));
        setBackgroundColor(COLOR_WHITE);
        setVisibility(View.VISIBLE);
    }

    @NonNull
    @Override
    public OptionsAdapter<OptionsContract.ViewModel, CardBlocksBoardWordListBinding> getAdapter() {
        return adapter;
    }

    @Override
    public void onRecyclerItemClick() {
//        super.onRecyclerItemClick();
        getClickEvent().postEvent(getProps());
    }

    public static class Factory extends MVVMViewModel.Factory<ItemViewModel> {

        @NonNull
        private final Board mProps;

        @NonNull
        private final OptionsViewModel.Factory mOptionsViewModel;

        public Factory(@NonNull final Application application,
                       @NonNull final OptionsViewModel.Factory factory,
                       @NonNull final Board props) {
            super(ItemViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
            mOptionsViewModel = Preconditions.requiresNonNull(factory, "Factory");
        }

        @NonNull
        @Override
        public ItemViewModel create() {
            return new ItemViewModel(mApplication, mOptionsViewModel, mProps);
        }
    }
}
