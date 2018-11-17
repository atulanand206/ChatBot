package com.creations.chatbot.network;

import com.creations.chatbot.callbacks.ListResponseCallback;
import com.creations.chatbot.callbacks.ObjectResponseCallback;
import com.creations.chatbot.model.Request;
import com.creations.chatbot.model.Response;

public interface IAPIChat {

    void getChatReply(Request request, ObjectResponseCallback<Response> responseCallback);

    void getChats(ListResponseCallback<Response> responseCallback);

}
