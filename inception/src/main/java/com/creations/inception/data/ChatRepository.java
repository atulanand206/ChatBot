package com.creations.inception.data;

import android.util.Log;

import com.creations.condition.Info;
import com.creations.inception.constants.AppConstants;
import com.creations.inception.model.APIResponse;
import com.creations.inception.model.ListItem;
import com.creations.inception.model.Request;
import com.creations.inception.model.User;
import com.creations.inception.network.IAPIChat;
import com.creations.inception.utils.FakeDataProvider;
import com.creations.tools.models.APIResponseBody;
import com.creations.tools.network.EmptyResponseCallback;
import com.creations.tools.network.ListResponseCallback;
import com.creations.tools.network.ObjectResponseCallback;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import io.realm.Realm;

public class ChatRepository {

    private static final String TAG = ChatRepository.class.getSimpleName();

    private IAPIChat apiChat;

    private Realm realm;

    public ChatRepository(IAPIChat apiChat) {
        this.apiChat = apiChat;
        realm = Realm.getDefaultInstance();
//        addFakeUsers();
    }

    public void addFakeUsers() {
        List<User> users = FakeDataProvider.getFakeUsers();
        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(users));
    }

    /**
     * Add a new user in the local database.
     * @param userName
     */
    public void addUser(String userName) {
        User user= new User(userName);
        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(user));
    }

    /**
     * @return The list of user present in the local database.
     */
    public List<User> getUsersFromLocal() {
        List<User> users = realm.where(User.class)
                .findAll();
        return realm.copyFromRealm(users);
    }

    /**
     * @param userName
     * @return User corresponding to a userName
     */
    public User getUser(String userName) {
        User user = realm.where(User.class)
                .equalTo("user", userName)
                .findFirst();
        return realm.copyFromRealm(user);
    }

    /**
     * Method to send a particular message specific to the user.
     * @param user
     * @param message
     * @param callback
     */
    public void sendMessage(User user, ListItem message, ObjectResponseCallback<APIResponse> callback) {

        user.getMessages().add(message);
        realm.executeTransaction(realm1 -> realm1.insertOrUpdate(user));
        Request request = new Request();
        request.setMessage(message.getMessage().getMessage());
        request.setExternalID(user.getUser());
        request.setApiKey(AppConstants.API_KEY);
        request.setChatBotID(AppConstants.CHAT_BOT_ID);

        apiChat.getAirspaces(new ListResponseCallback<Info>() {
            @Override
            public void onSuccess(List<Info> response) {
                Log.d(TAG, "Successs");
            }

            @Override
            public void onError(int statusCode, @NonNull String errorResponse, @NonNull APIResponseBody serializedErrorResponse, @Nullable Exception e) {
                Log.d(TAG, errorResponse);
            }
        });

        apiChat.getChatReply(request, new ObjectResponseCallback<APIResponse>() {
            @Override
            public void onSuccess(APIResponse response) {
                user.getMessages().add(new ListItem(response));
                realm.executeTransaction(realm1 -> realm1.insertOrUpdate(user));
                callback.onSuccess(response);
            }

            @Override
            public void onError(int responseCode, @NonNull String errorMessage, @NonNull APIResponseBody serializedErrorResponse, @Nullable Exception e) {
                callback.onError(responseCode,  errorMessage, serializedErrorResponse, e);
                if(responseCode == 500) {
                    request.setLeft(true);
                    request.setIsLeftId(nextLeftId());
                    realm.executeTransaction(realm1 -> realm1.insertOrUpdate(request));
                }
            }

        });
    }

    /**
     * Method to publish all the unsent messages when
     * Connection Receiver notifies that app is back online.
     */
    public void sendMessages(EmptyResponseCallback callback) {
        List<Request> requests = realm.copyFromRealm(
                realm.where(Request.class)
                .equalTo("isLeft",true)
                .findAll()
        );

        for(Request request : requests)
            apiChat.getChatReply(request, new ObjectResponseCallback<APIResponse>() {
                @Override
                public void onSuccess(APIResponse response) {
                    request.setLeft(false);
                    User user = realm.where(User.class)
                            .equalTo("user",request.getExternalID())
                            .findFirst();
                    if(user!=null) {
                        realm.executeTransaction(realm1 -> {
                            user.getMessages().add(new ListItem(response));
                            realm1.insertOrUpdate(user);
                            realm1.insertOrUpdate(request);
                        });
                    }
                    callback.onSuccess();
                }

                @Override
                public void onError(int statusCode, @NonNull String errorResponse, @NonNull APIResponseBody serializedErrorResponse, @Nullable Exception e) {

                }
            });
    }

    private int nextLeftId() {
        if(realm.where(Request.class).findAll().size()==0)
            return 0;
        return realm.where(Request.class).max("isLeftId").intValue();
    }

    @Override
    protected void finalize() throws Throwable {
        realm.close();
        super.finalize();
    }
}
