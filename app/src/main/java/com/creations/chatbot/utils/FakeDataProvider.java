package com.creations.chatbot.utils;

import com.creations.chatbot.model.ListItem;
import com.creations.chatbot.model.User;

import java.util.ArrayList;
import java.util.List;

import io.realm.RealmList;

public class FakeDataProvider {

    public static RealmList<ListItem> getFakeChats() {
        RealmList<ListItem> list = new RealmList<>();
        List<ListItem> items = getFakeChats(4);
        list.addAll(items);
        return list;
    }

    public static List<ListItem> getFakeChats(int count) {
        List<ListItem> items = new ArrayList<>();
        for(int i=0;i<count;i++)
            items.add(new ListItem());
        return items;
    }

    public static List<User> getFakeUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("121", getFakeChats()));
        users.add(new User("125", getFakeChats()));
        users.add(new User("123", getFakeChats()));
        users.add(new User("241", getFakeChats()));
        users.add(new User("271", getFakeChats()));
        users.add(new User("291", getFakeChats()));
        return users;
    }
}
