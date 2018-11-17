package com.creations.chatbot.network;

import com.creations.chatbot.callbacks.ListResponseCallback;
import com.creations.chatbot.callbacks.ObjectResponseCallback;
import com.creations.chatbot.model.APIResponse;
import com.creations.chatbot.model.Request;

public interface IAPIChat {

    void getChatReply(Request request, ObjectResponseCallback<APIResponse> responseCallback);

    void getChats(ListResponseCallback<APIResponse> responseCallback);

}
