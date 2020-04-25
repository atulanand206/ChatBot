package com.creations.inception.callbacks;

import com.android.volley.VolleyError;

public interface CompletionListener {

    void onSuccess();

    void onError(VolleyError error);
}
