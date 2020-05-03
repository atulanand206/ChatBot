package com.creations.tools.callback;

import androidx.annotation.NonNull;

public interface ObjectResponseCallback<T> extends ErrorResponseCallback {
    void onSuccess(@NonNull T response);
}