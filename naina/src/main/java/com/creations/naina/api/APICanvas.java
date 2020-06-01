package com.creations.naina.api;

import com.creations.bang.api.IAPIBang;
import com.creations.condition.Preconditions;
import com.creations.tools.callback.EmptyResponseCallback;
import com.creations.tools.network.NetworkManager;
import com.example.application.provider.IResourceProvider;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class APICanvas implements IAPIBang {

    private static final String TAG = APICanvas.class.getSimpleName();

    private final IResourceProvider mResourceProvider;
    private final NetworkManager mNetworkManager;

    public APICanvas(@NonNull final IResourceProvider resourceProvider,
                     @NonNull final NetworkManager networkManager) {
        mResourceProvider = Preconditions.requiresNonNull(resourceProvider, "ResourceProvider");
        mNetworkManager = Preconditions.requiresNonNull(networkManager, "NetworkManager");
    }

    String URL_WORD_POOL_HOSTNAME = "https://accounts.google.com/o/oauth2/v2/auth";

    private static class Auth {
        @SerializedName("client_id")
        private String clientId = "19537542645-ei581h5krka43fv2k4vv4ar1o3dq484u.apps.googleusercontent.com";

        @SerializedName("redirect_uri")
        private String redirectUri = "com.creations.naina";

        @SerializedName("scope")
        private String scope = "https://www.googleapis.com/auth/drive.file";

        @SerializedName("response_type")
        private String responseType = "code";
    }

    @Override
    public void authenticate(@NonNull final EmptyResponseCallback callback) {
//        mNetworkManager.makeObjectRequest(RequestMethod.POST, URL_WORD_POOL_HOSTNAME, new Auth(), new ObjectResponseCallback<Object>() {
//            @Override
//            public void onSuccess(@NonNull Object response) {
//                callback.onSuccess();
//            }
//
//            @Override
//            public void onError(int statusCode, @NonNull String errorResponse, @NonNull APIResponseBody serializedErrorResponse, @Nullable Exception e) {
//                callback.onError(statusCode, errorResponse, serializedErrorResponse, e);
//            }
//        }, Object.class, ContentType.FORM_ENCODED);
    }
}
