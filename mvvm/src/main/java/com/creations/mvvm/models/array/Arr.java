package com.creations.mvvm.models.array;

import com.creations.mvvm.models.blocks.Cell;
import com.creations.mvvm.models.props.Props;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.COLOR_ADD_ERROR;
import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.COLOR_ADD_GO;

public class Arr extends Props implements Serializable {

    private List<Item> items = new ArrayList<>();

    private int currentIndex = -1;

    public Arr() {

    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        clear();
        this.items.addAll(items);
    }

    public void clear() {
        this.currentIndex = -1;
        this.items.clear();
    }

    public void addItem(Item item) {
        this.items.add(item);
    }

    public void add(int x, int y, Cell cell) {
        this.items.add(new Item(x, y, cell));
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }

    public Arr valid() {
        for (Item item : items)
            item.setColorResId(COLOR_ADD_GO);
        return this;
    }

    public Arr invalid() {
        for (Item item : items)
            item.setColorResId(COLOR_ADD_ERROR);
        return this;
    }
}
