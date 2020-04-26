package com.creations.inception.utils;

import com.creations.inception.ui.form.RequestFragment;
import com.example.application.fragments.HostFragment;
import com.example.application.utils.MVVMInjector;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public interface FragmentHelper {
    static Fragment getRequestFragment(@NonNull final MVVMInjector builder) {
        return HostFragment.newInstance(RequestFragment.newInstance());
    }

}
