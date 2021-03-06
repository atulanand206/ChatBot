package com.creations.blocks.models;

import com.creations.mvvm.models.props.RecyclerProps;

import java.util.ArrayList;
import java.util.List;

public class Words extends RecyclerProps {

    private List<String> words = new ArrayList<>();

    public List<String> getWords() {
        return words;
    }

    public void setWords(List<String> words) {
        this.words = words;
    }
}
