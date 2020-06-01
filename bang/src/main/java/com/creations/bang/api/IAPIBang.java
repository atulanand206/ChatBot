package com.creations.bang.api;

import com.creations.tools.callback.EmptyResponseCallback;

import androidx.annotation.NonNull;

public interface IAPIBang {
    void authenticate(@NonNull EmptyResponseCallback callback);
}
