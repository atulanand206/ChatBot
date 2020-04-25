package com.creations.tools.network;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import androidx.annotation.StringDef;

public class RequestMethod {
    @StringDef({GET, POST, PUT, DELETE, PATCH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface HTTPRequestMethod {

    }

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String DELETE = "DELETE";
    public static final String PATCH = "PATCH";
}