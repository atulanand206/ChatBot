package com.creations.tools.network;

import com.creations.tools.models.APIResponseBody;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

interface ErrorResponseCallback {
    void onError(int statusCode, @NonNull String errorResponse,
                 @NonNull APIResponseBody serializedErrorResponse, @Nullable Exception e);
}