package com.creations.mvvm.models.blocks;

import com.creations.mvvm.models.array.Arr;
import com.creations.mvvm.models.array.Item;
import com.creations.mvvm.models.props.Props;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.COLOR_ADD_ERROR;
import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.COLOR_ADD_GO;
import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.COLOR_ADD_GREY;
import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.COLOR_NEAR;
import static com.creations.mvvm.ui.blocks.add.AddContract.ViewModel.COLOR_NORMAL;

public class Board extends Props implements Serializable {

    private int id;

    private String name;

    private String board;

    private List<Row> rows;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getBoard() {
        return board;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    @NonNull
    private final Arr selections = new Arr();

    public Board() {
        this.rows = new ArrayList<>();
    }

    public Board(int id, String name, String board) {
        this.id = id;
        this.name = name;
        this.board = board;
        this.rows = new ArrayList<>();
    }

    public Board(List<Row> rows) {
        this.rows = rows;
    }

    public List<Row> getRows() {
        return rows;
    }

    @NonNull
    public Arr getSelections() {
        return selections;
    }

    @Override
    public void setClickable(boolean clickable) {
        super.setClickable(clickable);
        for (Row row : rows)
            row.setClickable(clickable);
    }

    @Override
    public void setColorResId(int colorResId) {
        super.setColorResId(colorResId);
        for (Row row : rows)
            row.setColorResId(colorResId);
    }

    public void add(int x, int y, Cell cell) {
        selections.add(x, y, cell);
    }

    public Board refresh(final boolean valid) {
        if (selections.getItems().isEmpty()) {
            grayOut(false);
        } else {
            grayOut(true);
            enableRow(selections.getFirstAvailable());
            enableRow(selections.getLastAvailable());
            selected(selections.selected());
            addedCells(selections.getItems(), valid);
        }
        return this;
    }

    private void selected(int[] selected) {
        for (int indx : selected) {
            if (indx > -1 && indx < rows.size()) {
                rows.get(indx).setClickable(true);
                rows.get(indx).setColorResId(COLOR_ADD_GREY);
            }
        }
    }

    private void enableRow(int indx) {
        if (indx < 0 )
            return;
        if (rows.size() > indx) {
            rows.get(indx).setClickable(true);
            rows.get(indx).setColorResId(COLOR_NEAR);
        }
    }

    private void addedCells(List<Item> arr, final boolean valid) {
        for (Item item : arr) {
            Cell cell = item.getCell();
            cell.setClickable(true);
            cell.setColorResId(valid ? COLOR_ADD_GO : COLOR_ADD_ERROR);
            rows.get(item.getI()).getCells().get(item.getJ()).setProps(cell);
        }
    }

    private void grayOut(final boolean gray) {
        setClickable(!gray);
        setColorResId(gray ? COLOR_ADD_GREY : COLOR_NORMAL);
    }

    public Board clear() {
        selections.clear();
        return refresh(true);
    }

    public Board valid() {
        return refresh(true);
    }

    public Board invalid() {
        return refresh(false);
    }

    public void setRows() {

    }
}
