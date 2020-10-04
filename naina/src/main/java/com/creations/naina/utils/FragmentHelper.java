package com.creations.naina.utils;

import com.creations.naina.api.IConfigurationRepository;
import com.creations.naina.models.CanvasP;
import com.creations.naina.ui.container.ContainerFragment;
import com.creations.naina.ui.container.ContainerModule;
import com.example.application.utils.MVVMInjector;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public interface FragmentHelper {
    static Fragment getContainerFragment(@NonNull final CanvasP preset, @NonNull final MVVMInjector builder, @NonNull final IConfigurationRepository configurationRepository) {
        ContainerModule.ContainerSubcomponent.Builder builder1 = builder
                .getBuilder(ContainerFragment.class, ContainerModule.ContainerSubcomponent.Builder.class);
        return ContainerFragment.newInstance(preset, builder1, configurationRepository);
    }

}
