package com.creations.inception.network;

import com.creations.blocks.api.IAPIBlocks;
import com.creations.blocks.models.Board;
import com.creations.blocks.models.ScoreItem;
import com.creations.blocks.models.Word;
import com.creations.condition.Preconditions;
import com.creations.tools.callback.ListResponseCallback;
import com.creations.tools.callback.ObjectResponseCallback;
import com.creations.tools.network.NetworkManager;
import com.creations.tools.network.RequestMethod;

import androidx.annotation.NonNull;

import static com.creations.inception.constants.AppConstants.URL_WORD_BOARD;
import static com.creations.inception.constants.AppConstants.URL_WORD_POOL;
import static com.creations.inception.constants.AppConstants.URL_WORD_SCORE;
import static com.creations.inception.constants.AppConstants.URL_WORD_SCORES;

public class APIBlocks implements IAPIBlocks {

    private static final String TAG = APIBlocks.class.getSimpleName();

    private final NetworkManager mNetworkManager;

    public APIBlocks(@NonNull final NetworkManager networkManager) {
        mNetworkManager = Preconditions.requiresNonNull(networkManager, "NetworkManager");
    }

    @Override
    public void wordValidity(@NonNull final String word,
                             @NonNull final ObjectResponseCallback<Word> callback) {
        mNetworkManager.makeObjectRequest(RequestMethod.GET, String.format(URL_WORD_POOL, word), null, callback, Word.class);
    }

    @Override
    public void getBoards(@NonNull ListResponseCallback<Board> callback) {
        mNetworkManager.makeListRequest(RequestMethod.GET, URL_WORD_BOARD, null, callback, Board.class);
    }

    @Override
    public void postScore(@NonNull final ScoreItem score,
                          @NonNull final ObjectResponseCallback<ScoreItem> callback) {
        mNetworkManager.makeObjectRequest(RequestMethod.POST, URL_WORD_SCORE, score, callback, ScoreItem.class);
    }

    @Override
    public void getScores(@NonNull final ListResponseCallback<ScoreItem> callback) {
        mNetworkManager.makeListRequest(RequestMethod.GET, URL_WORD_SCORES, null, callback, ScoreItem.class);
    }
}
