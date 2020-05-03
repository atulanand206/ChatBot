package com.creations.mvvm.models;

import com.creations.mvvm.models.props.Props;
import com.creations.tools.callback.EmptyResponseCallback;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static com.example.application.constants.ApplicationConstants.FAKE_URL;

public class ImageData extends Props implements Serializable {

    private String mUrl;

    private final String mTitle;

    private final String mDescription;

    @Nullable
    private EmptyResponseCallback mImageLoadCallback;

    public ImageData() {
        mUrl = FAKE_URL;
        mTitle = "Trends for you";
        mDescription = "Select some topics you're interested in to help personalize your Twitter experience, starting with finding people to follow.";
    }

    public ImageData(String url) {
        mUrl = url;
        mTitle = "";
        mDescription = "";
    }

    public ImageData(String url, String title) {
        mUrl = url;
        mTitle = title;
        mDescription = "";
    }

    public ImageData(String url, String title, String description) {
        mUrl = url;
        mTitle = title;
        mDescription = description;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    @Nullable
    public EmptyResponseCallback getImageLoadCallback() {
        return mImageLoadCallback;
    }

    public void setImageLoadCallback(@NonNull EmptyResponseCallback imageLoadCallback) {
        this.mImageLoadCallback = imageLoadCallback;
    }
}
