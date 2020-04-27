package com.creations.mvvm.models.blocks;

import java.io.Serializable;
import java.util.List;

public class Board implements Serializable {

    private List<Row> rows;

    public Board(List<Row> rows) {
        this.rows = rows;
    }

    public List<Row> getRows() {
        return rows;
    }
}
