package com.creations.mvvm.ui.blocks.word;

import android.app.Application;

import com.creations.condition.Preconditions;
import com.creations.mvvm.models.array.Arr;
import com.creations.mvvm.models.array.Item;
import com.creations.mvvm.models.blocks.Cell;
import com.creations.mvvm.models.blocks.Word;
import com.creations.mvvm.models.blocks.Words;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.blocks.CellViewModel;
import com.creations.mvvm.ui.menu.MenuViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.creations.tools.utils.JsonConvertor;
import com.example.application.utils.AssetUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class WordViewModel extends MenuViewModel<Props> implements WordContract.ViewModel<Props> {

    @NonNull
    private final List<Cell> mCells = new ArrayList<>();

    @NonNull
    private final Set<String> mWords = new HashSet<>();

    public WordViewModel(@NonNull final Application application,
                         @NonNull final CellViewModel.Factory cellFactory,
                         @NonNull final JsonConvertor jsonConvertor,
                         @NonNull final Word props) {
        super(application, props);

        mContextCallback.postEvent((context -> {
            String json = AssetUtils.getJson(context, "words.json");
            mWords.addAll(jsonConvertor.fromJson(json, Words.class).getWords());
        }));
    }

    @NonNull
    public Set<String> getWords() {
        return mWords;
    }

    @Override
    public boolean valid() {
        return valid(getWord());
    }

    private boolean valid(String word) {
        return mWords.contains(word);
    }

    @Override
    public String getWord() {
        StringBuilder builder = new StringBuilder();
        for (Cell cell : mCells)
            builder.append(cell.getCharacter());
        return builder.toString();
    }

    @Override
    public void refresh(Arr selections) {
        mCells.clear();
        for (Item item : selections.getItems())
            mCells.add(item.getCell());
    }

    public static class Factory extends MVVMViewModel.Factory<WordViewModel> {

        @NonNull
        private final Word mProps;

        @NonNull
        private final CellViewModel.Factory mCellFactory;

        @NonNull
        private final JsonConvertor mJsonConvertor;

        public Factory(@NonNull final Application application,
                       @NonNull final CellViewModel.Factory cellFactory,
                       @NonNull final JsonConvertor jsonConvertor,
                       @NonNull final Word props) {
            super(WordViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
            mCellFactory = Preconditions.requiresNonNull(cellFactory, "CellFactory");
            mJsonConvertor = Preconditions.requiresNonNull(jsonConvertor, "JsonConvertor");
        }

        @NonNull
        @Override
        public WordViewModel create() {
            return new WordViewModel(mApplication, mCellFactory, mJsonConvertor, mProps);
        }
    }
}
