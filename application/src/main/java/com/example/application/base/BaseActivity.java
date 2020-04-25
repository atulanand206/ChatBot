package com.example.application.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.application.messages.IMessageManager;
import com.example.application.messages.MessageType;
import com.example.application.utils.ViewUtils;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

public abstract class BaseActivity extends FragmentActivity implements IMessageManager {
    private static final String TAG = BaseActivity.class.getSimpleName();

    private ConnectivityManager connectivityManager;
    private ConnectivityChangeReceiver connectivityChangeReceiver;
    private boolean isReceiverRegistered = false;

    @Inject IMessageManager messageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectivityManager = (ConnectivityManager) this.getSystemService(CONNECTIVITY_SERVICE);
        connectivityChangeReceiver = new ConnectivityChangeReceiver();
        showNoNetworkDialogIfNotActive();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isNetworkActive()) {
            registerConnectivityChangeReceiver();
        }
    }

    public Fragment getViewPagerFragmentById(final int pagerId, final int position) {
        String tag = "android:switcher:" +
                pagerId +
                ":" +
                position;
        return getSupportFragmentManager().findFragmentByTag(tag);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isReceiverRegistered) {
            unregisterReceiver(connectivityChangeReceiver);
            isReceiverRegistered = false;
        }
    }

    protected boolean isNetworkActive() {
        if (connectivityManager != null) {
            NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    private void showNoNetworkDialogIfNotActive() {
        if (!isNetworkActive()) {
            onNetworkStatusChanged(false);
        }
    }

    private void registerConnectivityChangeReceiver() {
        registerReceiver(connectivityChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        isReceiverRegistered = true;
    }

    private class ConnectivityChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            showNoNetworkDialogIfNotActive();
        }
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

    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (ViewUtils.isKeyboardShown(view.getRootView())) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected abstract void onNetworkStatusChanged(boolean isConnected);
}
