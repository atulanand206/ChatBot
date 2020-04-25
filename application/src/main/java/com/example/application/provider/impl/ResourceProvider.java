package com.example.application.provider.impl;

import android.content.Context;

import com.example.application.provider.IResourceProvider;

public class ResourceProvider implements IResourceProvider {

    private final Context context;

    public ResourceProvider(Context context) {
        this.context = context;
    }

    @Override
    public String getString(int stringResId, Object... args) {
        return context.getString(stringResId, args);
    }

    @Override
    public int getColor(int colorResId) {
        return context.getResources().getColor(colorResId, null);
    }

    @Override
    public float getDimen(int dimenResId) {
        return context.getResources().getDimension(dimenResId);
    }
}
