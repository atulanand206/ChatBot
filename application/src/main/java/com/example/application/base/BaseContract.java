package com.example.application.base;

import android.view.View;

import androidx.annotation.NonNull;

public interface BaseContract {

    interface BasePresenter<T> {

        /**
         * @param view the View instance to be set to presenter.
         */
        void setView(@NonNull T view);

        /**
         * resets the View instance to null in presenter.
         */
        void detachView();

        /**
         * To be called when presenter is started.
         */
        void start();

        /**
         * To be called to safely close the presenter when stopped.
         */
        void stop();
    }

    interface BaseView {

        /**
         * @return the Presenter attached to the view.
         */
        BasePresenter getPresenter();

        /**
         * Hides the keyboard if visible.
         * @param view the root view of the activity.
         */
        void hideKeyboard(View view);

        /**
         * @return true if the View is active in the lifecycle.
         */
        boolean isActive();

        /**
         * To be called when the view becomes visible.
         */
        void onVisible();

    }
}
