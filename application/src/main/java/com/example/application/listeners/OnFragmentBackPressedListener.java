package com.example.application.listeners;

/**
 * Created by Anil on 25/11/17.
 * Interface to let the active fragment know that back is pressed.
 */
public interface OnFragmentBackPressedListener {
    /**
     * @return true if the fragment handled the back press event false otherwise.
     */
    boolean onFragmentBackPressed();
}
