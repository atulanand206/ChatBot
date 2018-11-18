package com.creations.chatbot.ui.list;

import com.creations.chatbot.data.ChatRepository;
import com.creations.chatbot.model.User;

import java.util.List;

public class ListPresenter implements ListContract.Presenter {

    private List<User> users;

    private ListContract.View view;
    private ChatRepository repository;

    public ListPresenter(ListContract.View view, ChatRepository repository) {
        this.view = view;
        this.repository = repository;
        this.users = repository.getUsersFromLocal();
    }

    @Override
    public void start() {
        view.onItemsLoaded();
    }

    @Override
    public List<User> getUsers() {
        return users;
    }
}
