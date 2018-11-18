package com.creations.chatbot.data;

import com.creations.chatbot.callbacks.ObjectResponseCallback;
import com.creations.chatbot.constants.AppConstants;
import com.creations.chatbot.model.APIResponse;
import com.creations.chatbot.model.ListItem;
import com.creations.chatbot.model.Request;
import com.creations.chatbot.model.User;
import com.creations.chatbot.network.IAPIChat;
import com.creations.chatbot.utils.FakeDataProvider;

import java.util.List;

import io.realm.Realm;

public class ChatRepository {

    private IAPIChat apiChat;

    private Realm realm;

    public ChatRepository(IAPIChat apiChat) {
        this.apiChat = apiChat;
        realm = Realm.getDefaultInstance();
        addFakeUsers();
    }

    public void addFakeUsers() {
        List<User> users = FakeDataProvider.getFakeUsers();
        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(users));
    }

    public List<User> getUsersFromLocal() {
        List<User> users = realm.where(User.class)
                .findAll();
        return realm.copyFromRealm(users);
    }

    public User getUser(String userName) {
        User user = realm.where(User.class)
                .equalTo("user", userName)
                .findFirst();
        return realm.copyFromRealm(user);
    }

    public void sendMessage(User user, ListItem message, ObjectResponseCallback<APIResponse> callback) {

        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(user));
        Request request = new Request();
        request.setMessage(message.getMessage().getMessage());
        request.setExternalID(user.getUser());
        request.setApiKey(AppConstants.API_KEY);
        request.setChatBotID(AppConstants.CHAT_BOT_ID);

        apiChat.getChatReply(request, new ObjectResponseCallback<APIResponse>() {
            @Override
            public void onSuccess(APIResponse response) {
                user.getMessages().add(new ListItem(response));
                realm.executeTransaction(realm1 -> realm1.insertOrUpdate(user));
                callback.onSuccess(response);
            }

            @Override
            public void onError(int responseCode, String errorMessage) {
                callback.onError(responseCode,errorMessage);
            }
        });
    }

    @Override
    protected void finalize() throws Throwable {
        realm.close();
        super.finalize();
    }
}
