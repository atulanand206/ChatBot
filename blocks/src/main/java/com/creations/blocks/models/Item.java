package com.creations.blocks.models;

import com.creations.mvvm.models.props.Props;

import java.io.Serializable;

public class Item extends Props implements Serializable {

    private final int i;
    private final int j;

    private final Cell cell;

    public Item(int i, int j, Cell cell) {
        this.i = i;
        this.j = j;
        this.cell = cell;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public Cell getCell() {
        return cell;
    }
}
