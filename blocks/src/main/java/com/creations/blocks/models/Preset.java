package com.creations.blocks.models;

import com.creations.mvvm.models.props.Props;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.creations.blocks.ui.add.AddContract.ViewModel.COLOR_WHITE;

public class Preset extends Props implements Serializable {

    private List<Board> board;

    public Preset() {
        this.board = new ArrayList<>();
        setColorResId(COLOR_WHITE);
    }

    public Preset(List<Board> board) {
        this.board = board;
    }

    public List<Board> getBoard() {
        return board;
    }

    public void addBoard(Board board) {
        this.board.add(board);
    }

    public void addBoards(List<Board> response) {
        for (Board board : response)
            addBoard(board);
    }

    @Override
    public void setColorResId(int colorResId) {
        super.setColorResId(colorResId);
        for (Board brd : board)
            brd.setColorResId(colorResId);
    }
}
