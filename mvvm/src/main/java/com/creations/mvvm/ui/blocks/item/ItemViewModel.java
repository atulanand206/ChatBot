package com.creations.mvvm.ui.blocks.item;

import android.app.Application;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.View;

import com.creations.condition.Preconditions;
import com.creations.mvvm.R;
import com.creations.mvvm.databinding.CardBlocksBoardWordListBinding;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.blocks.Board;
import com.creations.mvvm.models.blocks.Row;
import com.creations.mvvm.ui.blocks.options.OptionsAdapter;
import com.creations.mvvm.ui.blocks.options.OptionsContract;
import com.creations.mvvm.ui.blocks.options.OptionsViewModel;
import com.creations.mvvm.ui.recycler.RecyclerListener;
import com.creations.mvvm.ui.recycler.RecyclerViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.example.application.utils.RecyclerUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.COLOR_WHITE;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class ItemViewModel extends RecyclerViewModel<Board> implements ItemContract.ViewModel<Board> {

    @NonNull
    private final MutableLiveData<SpannableString> string = new MutableLiveData<>();

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
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        List<OptionsContract.ViewModel> viewModels = new ArrayList<>();
        for (Row row : props.getRows()) {
            int start = spannableStringBuilder.length();
            String word = row.getWord().replace("\"","");
            spannableStringBuilder.append(word);
            int end = spannableStringBuilder.length();
            spannableStringBuilder.append(" ");
//            spannableStringBuilder.setSpan(new DrawableMarginSpan(null,  20), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//            spannableStringBuilder.setSpan(new RoundedBackgroundSpan(mApplication.getResources().getColor(R.color.pal_orange), mApplication.getResources().getColor(R.color.white)), start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            OptionsViewModel optionsViewModel = mFactory.create();
            optionsViewModel.setText(row.getWord().replace("\"",""));
            viewModels.add(optionsViewModel);
        }
        adapter.setViewModels(viewModels);
        spannableStringBuilder.append("\n");

        string.postValue(new SpannableString(spannableStringBuilder));
        setSubHeader(String.valueOf(props.getId()));
        setBackgroundColor(COLOR_WHITE);
        setVisibility(View.VISIBLE);
//                new Observer<LiveRunnable.Sentinel>() {
//            @Override
//            public void onChanged(LiveRunnable.Sentinel sentinel) {
//                if (sentinel != null)
//                    ItemViewModel.this.getPropsEvent().postEvent(props);
//            }
//        }
    }

    @NonNull
    @Override
    public MutableLiveData<SpannableString> getString() {
        return string;
    }

    @NonNull
    @Override
    public OptionsAdapter<OptionsContract.ViewModel, CardBlocksBoardWordListBinding> getAdapter() {
        return adapter;
    }

    @Override
    public void onRecyclerItemClick() {
        super.onRecyclerItemClick();
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
