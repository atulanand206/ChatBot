package com.creations.mvvm.models.blocks;

import com.creations.mvvm.models.props.Props;
import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class ScoreItem extends Props implements Serializable {

    @Expose(serialize = false)
    private long id;
    private String name;
    private long board;
    @Expose(serialize = false)
    private String boardname;
    private int score = 0;

    public int add() {
        score++;
        return score;
    }

    public int score() {
        return score;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getBoard() {
        return board;
    }

    public void setBoard(long board) {
        this.board = board;
    }

    public String getBoardname() {
        return boardname;
    }

    public void setBoardname(String boardname) {
        this.boardname = boardname;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
