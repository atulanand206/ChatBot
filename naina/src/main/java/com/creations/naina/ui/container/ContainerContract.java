package com.creations.naina.ui.container;

import android.widget.AdapterView;
import android.widget.CompoundButton;

import com.creations.bang.ui.bang.BangViewModel;
import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.menu.MenuContract;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public interface ContainerContract {

  interface ViewModel<T extends Props> extends MenuContract.ViewModel<T> {

    @NonNull
    MutableLiveData<Integer> getConfigurationIndex();

    @NonNull
    MutableLiveData<CharSequence[]> getEntries();

    @NonNull
    MutableLiveData<AdapterView.OnItemSelectedListener> getItemSelectedListener();

    @NonNull
    BangViewModel getBangViewModel();

    @NonNull
    LiveRunnable getUploadEvent();

    void onUploadClicked();

    @NonNull
    LiveRunnable.Mutable getDocumentEvent();

    void onDocumentClicked();
  }

  interface InteractionListener {

    void onUploadEventClicked();

    void onDocumentEventClicked();
  }
}
