package com.creations.blocks.api;

import com.creations.blocks.models.Board;
import com.creations.blocks.models.ScoreItem;
import com.creations.blocks.models.Word;
import com.creations.tools.callback.ListResponseCallback;
import com.creations.tools.callback.ObjectResponseCallback;

import androidx.annotation.NonNull;

public interface IAPIBlocks {

    void wordValidity(@NonNull String word,
                      @NonNull ObjectResponseCallback<Word> callback);

    void getBoards(@NonNull ListResponseCallback<Board> callback);

    void postScore(@NonNull ScoreItem score,
                   @NonNull ObjectResponseCallback<ScoreItem> callback);

    void getScores(@NonNull ListResponseCallback<ScoreItem> callback);
}
