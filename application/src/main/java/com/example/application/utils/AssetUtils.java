package com.example.application.utils;

import android.content.Context;

import java.io.IOException;
import java.io.InputStream;

import androidx.annotation.NonNull;

public class AssetUtils {

    public static String getJson(@NonNull final Context context,
                                 @NonNull final String fileName) {
        String json = null;
        try {
            InputStream is = context.getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }
}
