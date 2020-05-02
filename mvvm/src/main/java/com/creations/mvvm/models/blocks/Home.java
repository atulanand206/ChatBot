package com.creations.mvvm.models.blocks;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.blocks.add.AddContract;

import java.io.Serializable;

public class Home extends Props implements Serializable {

    public Home() {
        setColorResId(AddContract.ViewModel.COLOR_WHITE);
    }
}
