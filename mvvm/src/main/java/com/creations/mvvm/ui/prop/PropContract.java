package com.creations.mvvm.ui.prop;

import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.viewmodel.IMVVMViewModel;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public interface PropContract {

    interface ViewModel<T extends Props> extends IMVVMViewModel {

        @NonNull
        LiveData<Integer> getId();

        void setId(@IdRes int id);

        @NonNull
        T getProps();

        void setProps(@NonNull final T props);
    }

}
