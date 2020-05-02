package com.creations.mvvm.models.blocks;

import com.creations.mvvm.models.props.Props;

import java.io.Serializable;

public class Score extends Props implements Serializable {

   private ScoreItem scoreItem = new ScoreItem();

    public Score() {
    }

    public Score(ScoreItem scoreItem) {
        this.scoreItem = scoreItem;
    }

    public ScoreItem getScoreItem() {
        return scoreItem;
    }

    public void setScoreItem(ScoreItem scoreItem) {
        this.scoreItem = scoreItem;
    }
}
