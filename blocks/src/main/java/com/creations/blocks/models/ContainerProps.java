package com.creations.blocks.models;

import com.creations.mvvm.models.props.Props;

import java.io.Serializable;
import java.util.List;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;

public class ContainerProps extends Props implements Serializable {

    @DimenRes
    private int borderWidth;

    @NonNull
    private Board board;

    @NonNull
    private Score score;

    public ContainerProps(@NonNull final Board board) {
        this.borderWidth = com.example.application.R.dimen.grid_width;
        this.board = board;
        this.score = new Score();
    }

    public ContainerProps(@DimenRes final int borderWidth, @NonNull final Board board) {
        this.borderWidth = borderWidth;
        this.board = board;
        this.score = new Score();
    }

    @NonNull
    public Board getBoard() {
        return board;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    @NonNull
    public Score getScore() {
        return score;
    }

    public void selectRow(final int pos) {
        List<Row> rows = board.getRows();
        for (int i=0;i<rows.size();i++)
            rows.get(i).setClickable(pos == i);
    }

    public void deselectAll() {
        List<Row> rows = board.getRows();
        for (int i=0;i<rows.size();i++)
            rows.get(i).setClickable(true);
    }

}
