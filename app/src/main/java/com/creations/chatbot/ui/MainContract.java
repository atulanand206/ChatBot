package com.creations.chatbot.ui;

import com.creations.chatbot.model.ListItem;

import java.util.List;

public interface MainContract {

    interface View {

        void onItemsLoaded();
    }

    interface Presenter {

        void start();

        List<ListItem> getItems();

        void onSendClicked(String newEntry);
    }
}
