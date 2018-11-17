package com.creations.chatbot.network;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RequestMethod {
    @StringDef({GET, POST, PUT, DELETE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HTTPRequestMethod {

    }

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
}
