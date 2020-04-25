package com.example.application.fragments;


import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class HomePagerAdapter extends FragmentPagerAdapter {

    //region Variable Declaration
    private static final String TAG = HomePagerAdapter.class.getSimpleName();

    private List<Fragment> fragmentList;
    //endregion

    public HomePagerAdapter(@NonNull FragmentManager fm, @NonNull List<Fragment> fragmentList) {
        super(fm);

        Objects.requireNonNull(fm, "FragmentManager");
        Objects.requireNonNull(fragmentList, "FragmentList");

        this.fragmentList = fragmentList;
    }

    //region Inherited Methods
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position
        );
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentList.get(position).getClass().getSimpleName();
    }
    //endregion
}
