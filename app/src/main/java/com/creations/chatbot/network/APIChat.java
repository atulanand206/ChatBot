package com.creations.chatbot.network;

import android.net.Uri;

import com.creations.chatbot.callbacks.ListResponseCallback;
import com.creations.chatbot.callbacks.ObjectResponseCallback;
import com.creations.chatbot.constants.AppConstants;
import com.creations.chatbot.model.APIResponse;
import com.creations.chatbot.model.Request;

public class APIChat implements IAPIChat {

    private final NetworkManager networkManager;

    public APIChat(NetworkManager networkManager) {
        this.networkManager = networkManager;
    }

    private String buildRequest(String url, Request requestBody) {
        Uri uri = Uri.parse(url).buildUpon()
                .appendQueryParameter(AppConstants.Request.API_KEY, AppConstants.API_KEY)
                .appendQueryParameter(AppConstants.Request.CHAT_BOT_ID, AppConstants.CHAT_BOT_ID)
                .appendQueryParameter(AppConstants.Request.EXTERNAL_ID, requestBody.getExternalID())
                .appendQueryParameter(AppConstants.Request.MESSAGE, requestBody.getMessage())
                .build();
        return uri.toString();
    }

    @Override
    public void getChatReply(Request request, ObjectResponseCallback<APIResponse> responseCallback) {
        String url = buildRequest(AppConstants.URL, request);
        networkManager.makeObjectRequest(com.android.volley.Request.Method.GET,
                url,request,responseCallback,APIResponse.class, AppConstants.CHAT_API);
    }

    @Override
    public void getChats(ListResponseCallback<APIResponse> responseCallback) {

    }
}
