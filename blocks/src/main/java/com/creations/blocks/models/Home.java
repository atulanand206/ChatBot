package com.creations.blocks.models;

import com.creations.blocks.ui.add.AddContract;
import com.creations.mvvm.models.props.Props;

import java.io.Serializable;

public class Home extends Props implements Serializable {

    public Home() {
        setColorResId(AddContract.ViewModel.COLOR_WHITE);
    }
}
