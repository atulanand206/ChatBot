package com.creations.inception.utils;

import com.creations.condition.Info;
import com.creations.inception.ui.form.RequestFragment;
import com.creations.inception.ui.form.RequestModule;
import com.creations.mvvm.models.navigation.NavigationBarProps;
import com.creations.mvvm.models.navigation.NavigationState;
import com.example.application.fragments.HostFragment;
import com.example.application.utils.MVVMInjector;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public interface FragmentHelper {
    static Fragment getRequestFragment(@NonNull final MVVMInjector builder) {
        Info info = new Info();
        NavigationBarProps navigationBarProps = new NavigationBarProps.Builder()
                .withAdvisory(NavigationState.SKIPPED)
                .withChecklist(NavigationState.SKIPPED)
                .build();

        RequestModule.RequestSubcomponent.Builder subComponentBuilder = builder.getBuilder(
                RequestFragment.class, RequestModule.RequestSubcomponent.Builder.class
        );
        
        return HostFragment.newInstance(RequestFragment.newInstance(info, navigationBarProps, subComponentBuilder));
    }

}
