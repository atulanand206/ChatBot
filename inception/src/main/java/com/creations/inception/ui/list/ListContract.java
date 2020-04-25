package com.creations.inception.ui.list;

import com.creations.inception.models.User;

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
