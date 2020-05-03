package com.creations.blocks.models;

import com.creations.mvvm.models.props.Props;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Add extends Props implements Serializable {

    private List<Row> rows;

    public Add(List<Row> rows) {
        this.rows = rows;
    }

    public Add(Row integer) {
        this.rows = new ArrayList<>();
        rows.add(integer);
    }

    public List<Row> getRows() {
        return rows;
    }
}
