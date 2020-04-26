package com.example.application.utils;

import android.annotation.SuppressLint;
import android.view.Gravity;
import android.view.View;

import com.example.application.R;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

@SuppressLint("RtlHardcoded")
public class NavigationDrawer {

    public void openDrawer(@Nullable View rootView) {
        if (rootView == null)
            return;

        View drawerView = rootView.findViewById(R.id.left_drawer);
        if (!(drawerView instanceof DrawerLayout))
            return;
        DrawerLayout drawerLayout = (DrawerLayout) drawerView;
        drawerLayout.openDrawer(Gravity.LEFT);
    }

    public void closeDrawer(@Nullable View rootView) {
        if (rootView == null)
            return;

        View drawerView = rootView.findViewById(R.id.left_drawer);
        if (!(drawerView instanceof DrawerLayout))
            return;
        DrawerLayout drawerLayout = (DrawerLayout) drawerView;
        drawerLayout.closeDrawer(Gravity.LEFT);
    }
}
