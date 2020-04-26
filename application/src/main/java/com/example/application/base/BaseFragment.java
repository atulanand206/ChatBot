package com.example.application.base;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.application.listeners.OnFragmentBackPressedListener;
import com.example.application.messages.IMessageManager;
import com.example.application.messages.MessageType;
import com.example.application.utils.Animations;
import com.example.application.utils.ViewUtils;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment implements OnFragmentBackPressedListener,
        BaseContract.BaseView, IMessageManager {

    @Inject IMessageManager messageManager;

    @Inject Animations animations;

    protected Context context;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (ViewUtils.isKeyboardShown(view.getRootView())) {
            if (imm == null)
                return;
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
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
    public void onVisible() {

    }

    @Override
    public BaseContract.BasePresenter getPresenter() {
        return null;
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
    public void crossfade(@NonNull final View rootView) {
        animations.crossfade(context, rootView);
    }
}
