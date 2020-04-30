package com.creations.mvvm.models.blocks;

import com.creations.mvvm.models.array.Arr;
import com.creations.mvvm.models.array.Item;
import com.creations.mvvm.models.props.Props;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.COLOR_ADD_CLEAR;
import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.COLOR_NEAR;

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

    public Board refresh(Arr mCells) {
        grayOut();
        if (mCells.getCurrentIndex() != -1) {
            nextRow(mCells.getCurrentIndex());
            lastRow(mCells.getCurrentIndex());
        }
        addedCells(mCells);
        return this;
    }

    public static boolean proceed(List<Row> rows, int clickedIndex) {
        List<Cell> cells = rows.get(clickedIndex).getCells();
        int size = cells.size();
        return size < 2;
    }

    private void addedCells(Arr arr) {
        for (Item item : arr.getItems()) {
            Cell cell = item.getCell();
            rows.get(item.getI()).getCells().get(item.getJ()).setProps(cell);
        }
    }

    private void grayOut() {
        for (Row row : rows) {
            for (Cell cell : row.getCells()) {
                cell.setClickable(false);
                cell.setColorResId(COLOR_ADD_CLEAR);
            }
        }
    }

    private void nextRow(int finalI1) {
        if (rows.size() > finalI1 + 1) {
            Row nextRow = rows.get(finalI1 + 1);
            nextRow.setClickable(true);
            nextRow.setColorResId(COLOR_NEAR);
            rows.set(finalI1 + 1, nextRow);
        }
    }

    private void lastRow(int finalI1) {
        if (rows.size() > finalI1 - 1 && finalI1 > 0) {
            Row lastRow = rows.get(finalI1 - 1);
            lastRow.setClickable(true);
            lastRow.setColorResId(COLOR_NEAR);
            rows.set(finalI1 - 1, lastRow);
        }
    }
}
