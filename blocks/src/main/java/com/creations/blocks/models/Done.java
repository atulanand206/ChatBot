package com.creations.blocks.models;

import com.creations.mvvm.models.props.Props;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Done extends Props implements Serializable {

    private List<Row> rows;

    private String word = "dasadsasa";

    public Done() {
        this.rows = new ArrayList<>();
    }

    public Done(List<Row> rows) {
        this.rows = rows;
    }

    public Done(Row integer) {
        this.rows = new ArrayList<>();
        rows.add(integer);
    }

    public List<Row> getRows() {
        return rows;
    }

    public String getWord() {
        return word;
    }
}
