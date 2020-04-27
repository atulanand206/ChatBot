package com.creations.mvvm.models.props;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class DrawerProps extends Props implements Serializable {

    private final List<DrawerItem> drawerItems = new ArrayList<>();

    public DrawerProps(@NonNull final List<DrawerItem> drawerItems) {
        this.drawerItems.addAll(drawerItems);
    }

    public List<DrawerItem> getDrawerItems() {
        return drawerItems;
    }

    public void setDrawerItems(@NonNull final List<DrawerItem> drawerItems) {
        this.drawerItems.clear();
        this.drawerItems.addAll(drawerItems);
    }
}
