package com.creations.inception.ui.form;

import com.creations.mvvm.form.navigation.NavigationBarViewModel;
import com.creations.mvvm.models.navigation.NavigationBarProps;
import com.creations.mvvm.viewmodel.IMVVMViewModel;

import androidx.annotation.NonNull;

public interface RequestContract {

    interface ViewModel extends IMVVMViewModel {

        NavigationBarViewModel getNavigationBar();

        void setNavigationProps(@NonNull final NavigationBarProps props);

    }

    interface InteractionListener {

    }
}
