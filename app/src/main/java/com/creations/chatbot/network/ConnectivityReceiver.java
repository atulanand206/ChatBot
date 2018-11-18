package com.creations.chatbot.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.creations.chatbot.ChatBotApplication;

public class ConnectivityReceiver {

    public static boolean isConnected() {
        ConnectivityManager
                cm = (ConnectivityManager) ChatBotApplication.getInstance().getApplicationContext()
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
    }

}