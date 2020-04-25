package com.creations.mvvm.models.props;

import java.io.Serializable;

import static com.example.application.constants.ApplicationConstants.FAKE_URL;

public class ImageData implements Serializable {

    private final String mUrl;

    private final String mTitle;

    private final String mDescription;

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

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }
}
