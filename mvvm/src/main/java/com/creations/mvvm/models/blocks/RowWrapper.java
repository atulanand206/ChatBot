package com.creations.mvvm.models.blocks;

import com.creations.mvvm.models.props.Props;

import java.io.Serializable;

public class RowWrapper extends Props implements Serializable {

    private Row row;
    private int pos;

    public RowWrapper(Row row, int pos) {
        this.row = row;
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public Row getRow() {
        return row;
    }
}
