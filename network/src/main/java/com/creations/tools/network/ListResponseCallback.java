package com.creations.tools.network;

import java.util.List;

import androidx.annotation.NonNull;

public interface ListResponseCallback<T> extends ErrorResponseCallback {
    void onSuccess(@NonNull List<T> response);
}