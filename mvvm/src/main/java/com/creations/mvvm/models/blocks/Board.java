package com.creations.mvvm.models.blocks;

import com.creations.mvvm.models.props.Props;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board extends Props implements Serializable {

    private List<Row> rows;

    public Board(Row row) {
        this.rows = new ArrayList<>();
        this.rows.add(row);
    }

    public Board(List<Row> rows) {
        this.rows = rows;
    }

    public List<Row> getRows() {
        return rows;
    }
}
