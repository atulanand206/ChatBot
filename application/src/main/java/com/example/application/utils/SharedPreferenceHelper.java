package com.example.application.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import java.util.Objects;

public class SharedPreferenceHelper {

    private SharedPreferences sharedPreferences;

    private static final String sharedPrefsName = "UserInfo";
    private static final String PREFERENCE_VERSION = "pref_version";

    public SharedPreferenceHelper(@NonNull final Context context,
                                  @NonNull final String version){
        this.sharedPreferences = context.getSharedPreferences(sharedPrefsName, Context.MODE_PRIVATE);
        if(!Objects.requireNonNull(sharedPreferences.getString(PREFERENCE_VERSION, "")).equals(version)){
            sharedPreferences.edit().clear().apply();
            sharedPreferences.edit().putString(PREFERENCE_VERSION, version).apply();
        }
    }

    @SuppressLint("ApplySharedPref")
    public void saveSynchronously(@NonNull final String key, @NonNull final String value) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();

    }

    public void save(@NonNull final String key, @NonNull final String value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public void save(@NonNull final String key, final int value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void save(@NonNull final String key, final float value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.apply();
    }

    public void save(@NonNull final String key, final boolean value){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public String getString(@NonNull final String key){
        return sharedPreferences.getString(key,null);
    }

    public int getInt(@NonNull final String key){
        return sharedPreferences.getInt(key,0);
    }

    public float getFloat(@NonNull final String key){
        return sharedPreferences.getFloat(key,0);
    }

    public boolean getBoolean(@NonNull final String key){
        return sharedPreferences.getBoolean(key,false);
    }

}
