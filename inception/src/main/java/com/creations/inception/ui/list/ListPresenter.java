package com.creations.inception.ui.list;

import com.creations.inception.data.ChatRepository;
import com.creations.inception.models.User;

import java.util.List;
import java.util.Random;

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
        onItemsListChanged();
    }

    @Override
    public List<User> getUsers() {
        return users;
    }

    @Override
    public void addUser() {
        repository.addUser("User" + new Random().nextInt());
        onItemsListChanged();
    }

    @Override
    public void onItemsListChanged() {
        List<User> userList = repository.getUsersFromLocal();
        users.clear();
        users.addAll(userList);
        view.onItemsLoaded();
    }
}
