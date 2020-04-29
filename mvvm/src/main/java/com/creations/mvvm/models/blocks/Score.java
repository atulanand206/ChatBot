package com.creations.mvvm.models.blocks;

import com.creations.mvvm.models.props.Props;

import java.io.Serializable;

public class Score  extends Props implements Serializable {

    private int words = 0;

    public int add() {
        words++;
        return words;
    }

    public int score() {
        return words;
    }

    public String name() {
        return "ROWS";
    }
}
