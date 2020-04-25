package com.creations.inception.network;

import android.content.Context;
import android.net.Uri;

import com.creations.blogger.IAPIChat;
import com.creations.blogger.callback.ListResponseCallback;
import com.creations.blogger.callback.ObjectResponseCallback;
import com.creations.blogger.model.APIResponseBody;
import com.creations.blogger.model.blogger.PaginatedPost;
import com.creations.blogger.model.blogger.Post;
import com.creations.condition.Info;
import com.creations.condition.Preconditions;
import com.creations.inception.constants.AppConstants;
import com.creations.inception.models.Request;
import com.creations.tools.network.NetworkManager;
import com.creations.tools.network.RequestMethod;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.creations.inception.constants.AppConstants.URL_BLOGGER_GET_POSTS;
import static com.creations.inception.constants.AppConstants.URL_GET_AIRSPACE;

public class APIChat implements IAPIChat {

    private static final String TAG = APIChat.class.getSimpleName();

    private final Context mContext;
    private final NetworkManager mNetworkManager;

    public APIChat(@NonNull final Context context,
                   @NonNull final NetworkManager networkManager) {
        mContext = Preconditions.requiresNonNull(context, "Context");
        mNetworkManager = Preconditions.requiresNonNull(networkManager, "NetworkManager");
    }

    private String buildApiChatRequest(@NonNull final Request requestBody) {
        Uri uri = Uri.parse(AppConstants.URL_GET_API_CHAT).buildUpon()
                .appendQueryParameter(AppConstants.Request.API_KEY, AppConstants.API_KEY)
                .appendQueryParameter(AppConstants.Request.CHAT_BOT_ID, AppConstants.CHAT_BOT_ID)
                .appendQueryParameter(AppConstants.Request.EXTERNAL_ID, requestBody.getExternalID())
                .appendQueryParameter(AppConstants.Request.MESSAGE, requestBody.getMessage())
                .build();
        return uri.toString();
    }

//    @Override
//    public void getChatReply(@NonNull final Request request,
//                             @NonNull final ObjectResponseCallback<APIResponse> responseCallback) {
//        mNetworkManager.makeObjectRequest(RequestMethod.GET, buildApiChatRequest(request),
//                null, responseCallback, APIResponse.class);
//    }

    @Override
    public void getAirspaces(@NonNull final ListResponseCallback<Info> responseCallback) {
        mNetworkManager.makeListRequest(RequestMethod.GET, URL_GET_AIRSPACE,
                null, responseCallback, Info.class);
    }

    @Override
    public void getBlogPosts(@NonNull final ListResponseCallback<Post> callback) {
        mNetworkManager.makeObjectRequest(RequestMethod.GET, URL_BLOGGER_GET_POSTS, null, new ObjectResponseCallback<PaginatedPost>() {
            @Override
            public void onError(int statusCode, @NonNull String errorResponse, @NonNull APIResponseBody serializedErrorResponse, @Nullable Exception e) {
                callback.onError(statusCode, errorResponse, serializedErrorResponse, e);
            }

            @Override
            public void onSuccess(@NonNull PaginatedPost response) {
                callback.onSuccess(response.getItems());
            }
        }, PaginatedPost.class);
    }
}
