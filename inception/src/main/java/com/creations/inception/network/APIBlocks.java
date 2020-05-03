package com.creations.inception.network;

import com.creations.blocks.api.IAPIBlocks;
import com.creations.blocks.models.Board;
import com.creations.blocks.models.ScoreItem;
import com.creations.blocks.models.Word;
import com.creations.condition.Preconditions;
import com.creations.inception.R;
import com.creations.tools.callback.ListResponseCallback;
import com.creations.tools.callback.ObjectResponseCallback;
import com.creations.tools.network.NetworkManager;
import com.creations.tools.network.RequestMethod;
import com.example.application.provider.IResourceProvider;

import androidx.annotation.NonNull;

import static com.creations.inception.constants.AppConstants.URL_WORD_BOARD_ENDPOINT;
import static com.creations.inception.constants.AppConstants.URL_WORD_POOL;
import static com.creations.inception.constants.AppConstants.URL_WORD_SCORE;
import static com.creations.inception.constants.AppConstants.URL_WORD_SCORES;

public class APIBlocks implements IAPIBlocks {

    private static final String TAG = APIBlocks.class.getSimpleName();

    private final IResourceProvider mResourceProvider;
    private final NetworkManager mNetworkManager;

    public APIBlocks(@NonNull final IResourceProvider resourceProvider,
                     @NonNull final NetworkManager networkManager) {
        mResourceProvider = Preconditions.requiresNonNull(resourceProvider, "ResourceProvider");
        mNetworkManager = Preconditions.requiresNonNull(networkManager, "NetworkManager");
    }

    @Override
    public void wordValidity(@NonNull final String word,
                             @NonNull final ObjectResponseCallback<Word> callback) {
        mNetworkManager.makeObjectRequest(RequestMethod.GET, String.format(URL_WORD_POOL, word), null, callback, Word.class);
    }

    @Override
    public void getBoards(@NonNull ListResponseCallback<Board> callback) {
        mNetworkManager.makeListRequest(RequestMethod.GET, String.format("%s%s",mResourceProvider.getString(R.string.hostname), URL_WORD_BOARD_ENDPOINT), null, callback, Board.class);
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
