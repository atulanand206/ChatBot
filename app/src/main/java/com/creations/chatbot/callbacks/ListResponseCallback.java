package com.creations.chatbot.callbacks;

import java.util.List;

public interface ListResponseCallback<T> {

    void onSuccess(List<T> response);

    void onError(int responseCode, String errorMessage);
}
