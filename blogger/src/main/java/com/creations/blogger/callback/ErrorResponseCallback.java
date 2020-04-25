package com.creations.blogger.callback;

import com.creations.blogger.model.APIResponseBody;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface ErrorResponseCallback {
    void onError(int statusCode, @NonNull String errorResponse,
                 @NonNull APIResponseBody serializedErrorResponse, @Nullable Exception e);
}