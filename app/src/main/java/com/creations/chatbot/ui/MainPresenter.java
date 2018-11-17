package com.creations.chatbot.ui;

import com.creations.chatbot.model.ListItem;
import com.creations.chatbot.utils.FakeDataProvider;

import java.lang.ref.WeakReference;
import java.util.List;

public class MainPresenter implements MainContract.Presenter {

    private WeakReference<MainContract.View> view;

    private List<ListItem> items;

    public MainPresenter(MainContract.View view) {
        this.view = new WeakReference<>(view);
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

        MainContract.View mainView = view.get();
        if(view!=null) {
            mainView.onItemsLoaded();
        }

    }
}
