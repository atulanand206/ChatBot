package com.creations.inception.utils;

import com.creations.inception.ui.form.RequestFragment;
import com.creations.inception.ui.form.RequestModule;
import com.creations.mvvm.models.blocks.Preset;
import com.example.application.fragments.HostFragment;
import com.example.application.utils.MVVMInjector;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public interface FragmentHelper {
    static Fragment getRequestFragment(@NonNull final Preset preset, @NonNull final MVVMInjector builder) {
        RequestModule.RequestSubcomponent.Builder builder1 = ((RequestModule.RequestSubcomponent.Builder) builder.getBuilder(RequestFragment.class, RequestModule.Subcomponent.Builder.class));
        return HostFragment.newInstance(RequestFragment.newInstance(preset, builder1));
    }

}
