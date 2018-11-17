package com.creations.chatbot.network;

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

    @Override
    public void getChatReply(Request request, ObjectResponseCallback<APIResponse> responseCallback) {
        networkManager.makeObjectRequest(com.android.volley.Request.Method.GET,
                AppConstants.URL,request,responseCallback,APIResponse.class);
    }

    @Override
    public void getChats(ListResponseCallback<APIResponse> responseCallback) {

    }
}
