package com.creations.tools.callback;

import java.util.List;

import androidx.annotation.NonNull;

public interface ListResponseCallback<T> extends ErrorResponseCallback {
    void onSuccess(@NonNull List<T> response);
}