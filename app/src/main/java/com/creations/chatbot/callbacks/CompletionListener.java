package com.creations.chatbot.callbacks;

import com.android.volley.VolleyError;

public interface CompletionListener {

    void onSuccess();

    void onError(VolleyError error);
}
