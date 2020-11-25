package com.creations.script.utils;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.creations.script.models.CanvasP;
import com.creations.script.ui.container.ContainerFragment;
import com.creations.script.ui.container.ContainerModule;
import com.example.application.fragments.HostFragment;
import com.example.application.utils.MVVMInjector;

public interface FragmentHelper {
    static Fragment getContainerFragment(@NonNull final CanvasP preset, @NonNull final MVVMInjector builder) {
        ContainerModule.ContainerSubcomponent.Builder builder1 = ((ContainerModule.ContainerSubcomponent.Builder) builder
                .getBuilder(ContainerFragment.class, ContainerModule.ContainerSubcomponent.Builder.class));
        return HostFragment.newInstance(ContainerFragment.newInstance(preset, builder1));
    }

}
