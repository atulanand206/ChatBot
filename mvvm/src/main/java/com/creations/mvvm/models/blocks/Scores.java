package com.creations.mvvm.models.blocks;

import com.creations.mvvm.models.props.Props;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Scores extends Props implements Serializable {

    private List<ScoreItem> itemList;

    public Scores() {
        itemList = new ArrayList<>();
    }

    public Scores(List<ScoreItem> itemList) {
        this.itemList = itemList;
    }

    public List<ScoreItem> getItemList() {
        return itemList;
    }

    public void setItemList(List<ScoreItem> itemList) {
        this.itemList.clear();
        this.itemList.addAll(itemList);
    }
}
