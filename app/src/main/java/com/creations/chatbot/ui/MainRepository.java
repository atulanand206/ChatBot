package com.creations.chatbot.ui;

import com.creations.chatbot.callbacks.ObjectResponseCallback;
import com.creations.chatbot.constants.AppConstants;
import com.creations.chatbot.model.APIResponse;
import com.creations.chatbot.model.ListItem;
import com.creations.chatbot.model.Request;
import com.creations.chatbot.network.IAPIChat;

public class MainRepository {

    private IAPIChat apiChat;

    public MainRepository(IAPIChat apiChat) {
        this.apiChat = apiChat;
    }

    public void sendMessage(ListItem message, ObjectResponseCallback<APIResponse> callback) {

        Request request = new Request();
        request.setMessage(message.getMessage().getMessage());
        request.setExternalID(AppConstants.EXTERNAL_ID);

        apiChat.getChatReply(request, callback);
    }
}
