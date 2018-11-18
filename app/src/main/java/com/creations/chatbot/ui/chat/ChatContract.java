package com.creations.chatbot.ui.chat;

import com.creations.chatbot.model.APIResponse;
import com.creations.chatbot.model.ListItem;

import java.util.List;

public interface ChatContract {

    interface View {

        void onItemsLoaded();

        void refreshItems();
    }

    interface Presenter {

        void start();

        List<ListItem> getItems();

        void onSendClicked(String newEntry);

        void onReplyReceived(APIResponse response);

        void refreshItems();
    }
}
