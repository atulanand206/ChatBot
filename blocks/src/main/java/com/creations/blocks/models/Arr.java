package com.creations.blocks.models;

import com.creations.mvvm.models.props.Props;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.creations.blocks.ui.add.AddContract.ViewModel.COLOR_ADD_ERROR;
import static com.creations.blocks.ui.add.AddContract.ViewModel.COLOR_ADD_GO;

public class Arr extends Props implements Serializable {

    private List<Item> items = new ArrayList<>();

    private int firstAvailable = -1, lastAvailable = -1;

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        clear();
        this.items.addAll(items);
    }

    public void clear() {
        this.firstAvailable = -1;
        this.lastAvailable = -1;
        this.items.clear();
    }

    public void add(int x, int y, Cell cell) {
        if (firstAvailable == -1) {
            firstAvailable = x - 1;
            lastAvailable = x + 1;
            items.add(new Item(x, y, cell));
        } else {
            if (x == firstAvailable) {
                firstAvailable--;
                items.add(0, new Item(x, y, cell));
            } else if (x == lastAvailable) {
                lastAvailable++;
                items.add(new Item(x, y, cell));
            }
        }
        Item item = atIndex(x);
        if (item != null) {
            items.set(items.indexOf(item), new Item(x, y, cell));
        }
    }

    public int getFirstAvailable() {
        return firstAvailable;
    }

    public int getLastAvailable() {
        return lastAvailable;
    }

    public List<Item> valid() {
        for (Item item : items)
            item.setColorResId(COLOR_ADD_GO);
        return items;
    }

    public List<Item> invalid() {
        for (Item item : items)
            item.setColorResId(COLOR_ADD_ERROR);
        return items;
    }

    public int[] selected() {
        int[] s = new int[items.size()];
        for (int i=0;i<items.size();i++)
            s[i] = items.get(i).getI();
        return s;
    }

    public Item atIndex(final int indx) {
        for (int i=0;i<items.size();i++){
            if(items.get(i).getI() == indx)
                return items.get(i);
        }
        return null;
    }
}
