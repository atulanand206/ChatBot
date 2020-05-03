package com.creations.blocks.ui.word;

import android.app.Application;

import com.creations.blocks.api.IAPIBlocks;
import com.creations.blocks.models.Arr;
import com.creations.blocks.models.Cell;
import com.creations.blocks.models.Item;
import com.creations.blocks.models.Word;
import com.creations.blocks.models.Words;
import com.creations.blocks.ui.cell.CellViewModel;
import com.creations.condition.Preconditions;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.creations.tools.callback.ObjectResponseCallback;
import com.creations.tools.model.APIResponseBody;
import com.creations.tools.utils.JsonConvertor;
import com.example.application.utils.AssetUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class WordViewModel extends MenuViewModel<Props> implements WordContract.ViewModel<Props> {

    @NonNull
    private final IAPIBlocks mApiChat;

    @NonNull
    private final List<Cell> mCells = new ArrayList<>();

    @Nullable
    private Word word;

    @NonNull
    private final Set<String> mWords = new HashSet<>();

    public WordViewModel(@NonNull final Application application,
                         @NonNull final CellViewModel.Factory cellFactory,
                         @NonNull final JsonConvertor jsonConvertor,
                         @NonNull final IAPIBlocks apiChat,
                         @NonNull final Word props) {
        super(application, props);
        mApiChat = apiChat;
        mContextCallback.postEvent((context -> {
            String json = AssetUtils.getJson(context, "words.json");
            mWords.addAll(jsonConvertor.fromJson(json, Words.class).getWords());
        }));
    }

    @NonNull
    @Override
    public List<Cell> getCells() {
        return mCells;
    }

    @NonNull
    public Set<String> getWords() {
        return mWords;
    }

    @Override
    public void valid(@NonNull final ObjectResponseCallback<Word> callback) {
        if (getWord() == null)
            return;
        mApiChat.wordValidity(getWord(), new ObjectResponseCallback<Word>() {
            @Override
            public void onSuccess(@NonNull Word response) {
                callback.onSuccess(response);
            }

            @Override
            public void onError(int statusCode, @NonNull String errorResponse, @NonNull APIResponseBody serializedErrorResponse, @Nullable Exception e) {
                callback.onError(statusCode, errorResponse, serializedErrorResponse, e);
            }
        });
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

        @NonNull
        private final IAPIBlocks mApiChat;

        public Factory(@NonNull final Application application,
                       @NonNull final CellViewModel.Factory cellFactory,
                       @NonNull final JsonConvertor jsonConvertor,
                       @NonNull final IAPIBlocks apiChat,
                       @NonNull final Word props) {
            super(WordViewModel.class, application);
            mProps = Preconditions.requiresNonNull(props, "Props");
            mCellFactory = Preconditions.requiresNonNull(cellFactory, "CellFactory");
            mJsonConvertor = Preconditions.requiresNonNull(jsonConvertor, "JsonConvertor");
            mApiChat = Preconditions.requiresNonNull(apiChat, "ApiChat");
        }

        @NonNull
        @Override
        public WordViewModel create() {
            return new WordViewModel(mApplication, mCellFactory, mJsonConvertor, mApiChat, mProps);
        }
    }
}
