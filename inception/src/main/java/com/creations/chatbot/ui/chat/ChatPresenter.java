package com.creations.chatbot.ui.chat;

import com.creations.chatbot.data.ChatRepository;
import com.creations.chatbot.model.APIResponse;
import com.creations.chatbot.model.ListItem;
import com.creations.chatbot.model.User;
import com.creations.tools.models.APIResponseBody;
import com.creations.tools.network.ObjectResponseCallback;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ChatPresenter implements ChatContract.Presenter {

    private ChatContract.View view;
    private ChatRepository repository;
    private User user;

    private List<ListItem> items;

    public ChatPresenter(ChatContract.View view, ChatRepository repository, User user) {
        this.view = view;
        this.repository = repository;
        this.user = user;
        this.items = user.getMessages();
    }

    @Override
    public void start() {
        if(items.size()!=0)
            view.onItemsLoaded();
    }

    @Override
    public List<ListItem> getItems() {
        return items;
    }

    @Override
    public void refreshItems() {
        List<ListItem> list = repository.getUser(user.getUser()).getMessages();
        items.clear();
        items.addAll(list);
        view.onItemsLoaded();
    }

    @Override
    public void onSendClicked(String newEntry) {
        ListItem item = new ListItem(newEntry);

        repository.sendMessage(user, item, new ObjectResponseCallback<APIResponse>() {
            @Override
            public void onSuccess(APIResponse response) {
                onReplyReceived(response);
            }

            @Override
            public void onError(int statusCode, @NonNull String errorResponse, @NonNull APIResponseBody serializedErrorResponse, @Nullable Exception e) {

            }
        });

        view.onItemsLoaded();

    }

    @Override
    public void onReplyReceived(APIResponse response) {
        ListItem item = new ListItem(response);
//        items.add(item);

        view.onItemsLoaded();
    }
}
