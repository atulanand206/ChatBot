package com.creations.chatbot.network;

import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.VolleyError;
import com.creations.chatbot.callbacks.CompletionListener;
import com.creations.chatbot.constants.AppConstants;

import java.net.HttpURLConnection;

class RefreshPolicy extends DefaultRetryPolicy implements CompletionListener {

    private static final String TAG = RefreshPolicy.class.getSimpleName();

    private VolleyManager volleyManager;
    private CBRequest request;
    private CompletionListener completionListener;


    RefreshPolicy(VolleyManager volleyManager, CBRequest request, CompletionListener completionListener) {
        this.volleyManager = volleyManager;
        this.request = request;
        this.completionListener = completionListener;
    }

    @Override
    public void retry(VolleyError error) throws VolleyError {
        final int status = error.networkResponse.statusCode;
        if(hasAttemptRemaining() &&
                HttpURLConnection.HTTP_MOVED_PERM == status ||
                HttpURLConnection.HTTP_MOVED_TEMP == status ||
                HttpURLConnection.HTTP_SEE_OTHER == status) {
            final String location = error.networkResponse.headers.get("Location");
            Log.d(TAG, "Location: " + location);
            final CBRequest retryRequest = request.clone(location);
            // Construct a request clone and change the url to redirect location.
            volleyManager.addToRequestQueue(retryRequest, retryRequest.getTag() == null ? AppConstants.DEFAULT_TAG : retryRequest.getTag().toString());
            throw error;
        }
        super.retry(error);
    }

    @Override
    public void onSuccess() {
        completionListener.onSuccess();
    }

    @Override
    public void onError(VolleyError error) {
        completionListener.onError(error);
    }
}
