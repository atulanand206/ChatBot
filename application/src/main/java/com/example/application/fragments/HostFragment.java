package com.example.application.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.application.R;
import com.example.application.base.BaseFragment;
import com.example.application.listeners.OnFragmentBackPressedListener;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Hosts child fragments and manages the back stack
 */
public class HostFragment extends BaseFragment {
    private static final String TAG = HostFragment.class.getSimpleName();

    private Fragment fragment;
    private boolean isVisibleToUser = false;

    public static HostFragment newInstance(@NonNull Fragment fragment) {
        Objects.requireNonNull(fragment, "Fragment");

        HostFragment hostFragment = new HostFragment();
        hostFragment.fragment = fragment;
        return hostFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(inflater, "LayoutInflater");

        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_host, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Objects.requireNonNull(view, "View");

        super.onViewCreated(view, savedInstanceState);
        if (fragment != null) {
            addFragment(fragment, false);
        }
        notifyActiveChildAboutVisibility();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        for (Fragment fragment : getChildFragmentManager().getFragments()) {
            Log.d(TAG, "onActivityResult: " + fragment.getClass().getSimpleName());
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        if (isAdded()) {
            notifyActiveChildAboutVisibility();
        }
    }

    private void notifyActiveChildAboutVisibility() {
        FragmentManager fm = getChildFragmentManager();
        int totalFragments = fm.getFragments().size();
        if (totalFragments > 0) {
            Fragment activeFragment = fm.getFragments().get(totalFragments - 1);
            if (activeFragment != null && activeFragment.isAdded())
                activeFragment.setUserVisibleHint(isVisibleToUser);
        }
    }

    //region Methods
    public void addFragment(@NonNull final Fragment fragment, final boolean addToBackstack) {
        try {
            addFragmentInternal(fragment, addToBackstack);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addFragmentInternal(@NonNull final Fragment fragment, final boolean addToBackstack) {
        Objects.requireNonNull(fragment, "Fragment");

        FragmentManager fm = getChildFragmentManager();
        int fragmentsCount = fm.getFragments().size();
        if (fragmentsCount>0) {
            Fragment previousFragment = fm.getFragments().get(fragmentsCount - 1);
            setFragmentVisibility(previousFragment, GONE);
        }
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.add(R.id.hosted_fragment, fragment);
        if (addToBackstack) {
            fragmentTransaction.addToBackStack(null);
        }
        fragmentTransaction.commit();
        fm.executePendingTransactions();
    }

    @Override
    public boolean onFragmentBackPressed() {
        try {
            return onFragmentBackPressedInternal();
        } catch (Exception e) {
            return false;
        }
    }

    private boolean onFragmentBackPressedInternal() {
        FragmentManager fm = getChildFragmentManager();
        List<Fragment> fragments = fm.getFragments();
        if (!fragments.isEmpty()) {
            // latest fragment is always at the end of the list
            Fragment fragment = fragments.get(fragments.size() - 1);
            if (fragment instanceof OnFragmentBackPressedListener) {
                boolean isBackHandled = ((OnFragmentBackPressedListener) fragment).onFragmentBackPressed();
                if (isBackHandled)
                    return true;
            }
        }
        int backStackEntryCount = fm.getBackStackEntryCount();
        if (backStackEntryCount > 0 && backStackEntryCount < fragments.size()) {
            fm.popBackStackImmediate();
            Fragment previousFragment = fragments.get(fm.getBackStackEntryCount());
            if (previousFragment != null) {
                previousFragment.onResume();
                setFragmentVisibility(previousFragment, VISIBLE);
            }
            return true;
        }
        return false;
    }

    private void setFragmentVisibility(@NonNull final Fragment fragment, final int visibility) {
        View previousFragmentView = fragment.getView();
        if (previousFragmentView != null)
            previousFragmentView.setVisibility(visibility);
    }

    public void clearBackStack() {
        try {
            clearBackStackInternal();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearBackStackInternal() {
        FragmentManager fm = getChildFragmentManager();
        int count = fm.getBackStackEntryCount();
        for (int i = 0; i <= count; ++i) {
           fm.popBackStackImmediate();
        }
//        TODO: Remove onResume
        if(fm.getFragments().size()==1) {
            Fragment fragment = fm.getFragments().get(0);
            fragment.onResume();
            setFragmentVisibility(fragment, VISIBLE);
        }
    }

    public Fragment getCurrentFragment(){
        return fragment;
    }

    //endregion

    //region Interfaces
    public interface OnAddContentFragment {
        void addContentFragment(@NonNull final Fragment contentFragment);
        void removeContentFragment();
    }
    //endregion



}