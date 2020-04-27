package com.creations.mvvm.models.blocks;

import com.creations.mvvm.models.props.Props;

import java.util.List;

public class Row extends Props {

    private List<Cell> cells;

    public Row(List<Cell> cells) {
        this.cells = cells;
    }

    public List<Cell> getCells() {
        return cells;
    }
}
