package com.creations.mvvm.constants;

import com.creations.blogger.callback.ListResponseCallback;
import com.creations.blogger.callback.ObjectResponseCallback;
import com.creations.mvvm.models.blocks.Board;
import com.creations.mvvm.models.blocks.ScoreItem;
import com.creations.mvvm.models.blocks.Word;

import androidx.annotation.NonNull;

public interface IAPIChat {

//    void getAirspaces(ListResponseCallback<Info> responseCallback);
//
//    void getBlogPosts(@NonNull ListResponseCallback<Post> callback);

    void wordValidity(@NonNull String word,
                      @NonNull ObjectResponseCallback<Word> callback);

    void getBoards(@NonNull ListResponseCallback<Board> callback);

    void postScore(@NonNull ScoreItem score,
                   @NonNull ObjectResponseCallback<ScoreItem> callback);

    void getScores(@NonNull ListResponseCallback<ScoreItem> callback);
}
