package com.creations.tools.callback;

import com.creations.tools.model.APIResponseBody;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public interface ErrorResponseCallback {
    void onError(int statusCode, @NonNull String errorResponse,
                 @NonNull APIResponseBody serializedErrorResponse, @Nullable Exception e);
}