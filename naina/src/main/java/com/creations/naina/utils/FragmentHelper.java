package com.creations.naina.utils;

import com.creations.naina.models.CanvasP;
import com.creations.naina.ui.container.ContainerFragment;
import com.creations.naina.ui.container.ContainerModule;
import com.example.application.fragments.HostFragment;
import com.example.application.utils.MVVMInjector;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public interface FragmentHelper {
    static Fragment getContainerFragment(@NonNull final CanvasP preset, @NonNull final MVVMInjector builder) {
        ContainerModule.ContainerSubcomponent.Builder builder1 = builder
                .getBuilder(ContainerFragment.class, ContainerModule.ContainerSubcomponent.Builder.class);
        return HostFragment.newInstance(ContainerFragment.newInstance(preset, builder1));
    }

}
