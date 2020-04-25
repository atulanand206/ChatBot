package com.creations.inception.ui.chat;

import com.creations.inception.model.APIResponse;
import com.creations.inception.model.ListItem;

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
