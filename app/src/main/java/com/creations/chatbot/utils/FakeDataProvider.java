package com.creations.chatbot.utils;

import com.creations.chatbot.model.ListItem;

import java.util.ArrayList;
import java.util.List;

public class FakeDataProvider {

    public static List<ListItem> getFakeChats(int count) {
        List<ListItem> items = new ArrayList<>();
        for(int i=0;i<count;i++)
            items.add(new ListItem());
        return items;
    }
}
