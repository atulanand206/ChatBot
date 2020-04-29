package com.creations.mvvm.models.blocks;

import com.creations.mvvm.models.props.Props;

import java.io.Serializable;

import androidx.annotation.DimenRes;
import androidx.annotation.NonNull;

public class ContainerProps extends Props implements Serializable {

    @DimenRes
    private int borderWidth;

    @NonNull
    private Board board;

    public ContainerProps(@NonNull final Board board) {
        this.borderWidth = com.example.application.R.dimen.grid_width;
        this.board = board;
    }

    public ContainerProps(@DimenRes final int borderWidth, @NonNull final Board board) {
        this.borderWidth = borderWidth;
        this.board = board;
    }

    @NonNull
    public Board getBoard() {
        return board;
    }

    public int getBorderWidth() {
        return borderWidth;
    }
}