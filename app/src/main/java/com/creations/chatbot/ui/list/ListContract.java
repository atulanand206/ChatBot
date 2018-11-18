package com.creations.chatbot.ui.list;

import com.creations.chatbot.model.User;

import java.util.List;

public interface ListContract {

    interface View {

        void onItemsLoaded();
    }

    interface Presenter {

        void start();

        List<User> getUsers();

        void addUser();

        void onItemsListChanged();
    }
}
