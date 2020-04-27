package com.creations.mvvm.models.blocks;

import java.util.List;

public class Row {

    private List<Cell> cells;

    public Row(List<Cell> cells) {
        this.cells = cells;
    }

    public List<Cell> getCells() {
        return cells;
    }
}
