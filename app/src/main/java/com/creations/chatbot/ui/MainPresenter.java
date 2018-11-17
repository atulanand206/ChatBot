package com.creations.chatbot.ui;

import com.creations.chatbot.callbacks.ObjectResponseCallback;
import com.creations.chatbot.model.APIResponse;
import com.creations.chatbot.model.ListItem;
import com.creations.chatbot.utils.FakeDataProvider;

import java.util.List;

public class MainPresenter implements MainContract.Presenter {

    private MainContract.View view;
    private MainRepository repository;

    private List<ListItem> items;

    public MainPresenter(MainContract.View view, MainRepository repository) {
        this.view = view;
        this.repository = repository;
        this.items = FakeDataProvider.getFakeChats(10);
    }

    @Override
    public void start() {

    }

    @Override
    public List<ListItem> getItems() {
        return items;
    }

    @Override
    public void onSendClicked(String newEntry) {
        ListItem item = new ListItem(newEntry);
        items.add(item);

        repository.sendMessage(item, new ObjectResponseCallback<APIResponse>() {
            @Override
            public void onSuccess(APIResponse response) {
                onReplyReceived(response);
            }

            @Override
            public void onError(int responseCode, String errorMessage) {

            }
        });

        view.onItemsLoaded();

    }

    @Override
    public void onReplyReceived(APIResponse response) {
        ListItem item = new ListItem(response);
        items.add(item);

        view.onItemsLoaded();
    }
}
