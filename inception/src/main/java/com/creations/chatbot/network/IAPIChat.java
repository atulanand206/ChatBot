package com.creations.chatbot.network;

import com.creations.chatbot.model.APIResponse;
import com.creations.chatbot.model.Airspace;
import com.creations.chatbot.model.Request;
import com.creations.tools.network.ListResponseCallback;
import com.creations.tools.network.ObjectResponseCallback;

public interface IAPIChat {

    void getChatReply(Request request, ObjectResponseCallback<APIResponse> responseCallback);

    void getAirspaces(ListResponseCallback<Airspace> responseCallback);

}
