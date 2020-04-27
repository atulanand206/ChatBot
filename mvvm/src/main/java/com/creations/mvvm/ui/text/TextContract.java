package com.creations.mvvm.ui.text;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.prop.PropContract;

public interface TextContract {

    interface ViewModel<T extends Props> extends PropContract.ViewModel<T> {

    }

}
