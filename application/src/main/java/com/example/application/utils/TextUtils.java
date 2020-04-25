package com.example.application.utils;

import android.annotation.SuppressLint;

import androidx.annotation.Nullable;

@SuppressLint("DefaultLocale")
public final class TextUtils {

    public static boolean isEmpty(@Nullable final CharSequence str) {
        return str == null || str.length() == 0;
    }

    public static boolean isDigitsOnly(CharSequence str) {
        final int len = str.length();
        for (int cp, i = 0; i < len; i += Character.charCount(cp)) {
            cp = Character.codePointAt(str, i);
            if (!Character.isDigit(cp)) {
                return false;
            }
        }
        return true;
    }
    public static double parseDouble(double val, int keep) {
        String id = "%." + keep + "f";
        return Double.parseDouble(String.format(id, val));
    }

    public static String getHyphenatedPhone(String phone) {
        return phone.substring(0, 3) +
                "-" +
                phone.substring(3, 6) +
                "-" +
                phone.substring(6, 10);
    }

    public static String getPhoneFromHyphenated(String phone) {
        return phone.replace("-","");
    }
}
