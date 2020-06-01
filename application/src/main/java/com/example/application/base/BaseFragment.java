package com.example.application.base;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.application.listeners.OnFragmentBackPressedListener;
import com.example.application.messages.IMessageManager;
import com.example.application.messages.MessageType;
import com.example.application.utils.Animations;
import com.example.application.utils.NavigationDrawer;
import com.example.application.utils.ViewUtils;

import javax.inject.Inject;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public abstract class BaseFragment extends Fragment implements OnFragmentBackPressedListener,
        BaseContract.BaseView, IMessageManager {

    @Inject IMessageManager messageManager;

    @Inject Animations animations;

    @Inject NavigationDrawer drawer;

    protected Context context;

    @Nullable
    protected View mRootView;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void hideKeyboard(@NonNull final View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (ViewUtils.isKeyboardShown(view.getRootView())) {
            if (imm == null)
                return;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
//        }
        hideNavigation(view);
    }

    public void hideNavigation(@NonNull final View view) {
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setHasOptionsMenu(boolean hasMenu) {
        super.setHasOptionsMenu(hasMenu);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        setMenuVisibility(isVisibleToUser);
    }

    @Override
    public boolean onFragmentBackPressed() {
        return false;
    }

    @Override
    public void showToast(@NonNull String message) {
        showToast(message, MessageType.SUCCESS, Toast.LENGTH_SHORT);
    }

    @Override
    public void showToast(@NonNull String message, @NonNull MessageType messageType, int duration) {
        messageManager.showToast(message , messageType, duration);
    }

    @Override
    public void showToast(int stringResId, @NonNull MessageType messageType, int duration) {
        messageManager.showToast(stringResId , messageType, duration);
    }

    @Override
    public void showSnackBar(@NonNull View view, @NonNull String message, @NonNull MessageType messageType) {
        messageManager.showSnackBar(view, message, messageType);
    }

    @Override
    public void showSnackBar(@NonNull View view, int stringResId, @NonNull MessageType messageType) {
        messageManager.showSnackBar(view, stringResId, messageType);
    }

    @Override
    public void showLastSnackBar(@NonNull View view) {
        messageManager.showLastSnackBar(view);
    }

    @Override
    public void hideSnackBar() {
        messageManager.hideSnackBar();
    }

    @Override
    public void crossfade() {
        animations.crossfade(context, mRootView);
    }

    @Override
    public void openDrawer() {
        drawer.openDrawer(mRootView);
    }

    @Override
    public void closeDrawer() {
        drawer.closeDrawer(mRootView);
    }

    protected void setStatusBarColor(@NonNull final FragmentActivity appCompatActivity, @ColorRes int colorInt) {
        if (appCompatActivity instanceof AppCompatActivity)
            ViewUtils.setStatusBarColor(((AppCompatActivity) appCompatActivity), colorInt);
    }
}
