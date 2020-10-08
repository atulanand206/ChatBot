package com.creations.naina.ui.container;

import android.widget.AdapterView;

import com.creations.bang.ui.bang.BangViewModel;
import com.creations.mvvm.live.LiveEvent;
import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.Props;
import com.creations.naina.ui.contact.ContactAdapter;
import com.creations.mvvm.ui.menu.MenuContract;
import com.creations.mvvm.ui.text.TextViewModel;

import androidx.annotation.NonNull;

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
    TextViewModel getEntityNameViewModel();

    @NonNull
    TextViewModel getProprietor();

    @NonNull
    TextViewModel getWorkAddress();

    @NonNull
    TextViewModel getSiteAddress();

    @NonNull
    TextViewModel getGstIn();

    @NonNull
    TextViewModel getMobileNumber();

    @NonNull
    TextViewModel getBankDetails();

    @NonNull
    TextViewModel getAccountNumber();

    @NonNull
    TextViewModel getIfsc();

    @NonNull
    TextViewModel getAuthorisedSignatory();

    @NonNull
    TextViewModel getGst();

    @NonNull
    TextViewModel getCgst();

    @NonNull
    TextViewModel getSgst();

    @NonNull
    TextViewModel getIgst();

    @NonNull
    TextViewModel getRate();

    @NonNull
    ContactAdapter getContactAdapter();

    @NonNull
    MutableLiveData<String> getInputFileName();

    @NonNull
    TextViewModel getOutputFileName();

    @NonNull
    LiveRunnable getUploadEvent();

    void onUploadClicked();

    @NonNull
    LiveEvent.Mutable<String> getDocumentEvent();

    void onDocumentClicked();

    @NonNull
    MutableLiveData<Boolean> getEntityExpanded();

    @NonNull
    LiveRunnable.Mutable getEntityExpandEvent();

    void expandEntity();

    @NonNull
    MutableLiveData<Boolean> getBankExpanded();

    @NonNull
    LiveRunnable.Mutable getBankExpandEvent();

    void expandBank();

    @NonNull
    MutableLiveData<Boolean> getRateExpanded();

    @NonNull
    LiveRunnable.Mutable getRateExpandEvent();

    void expandRate();

    @NonNull
    MutableLiveData<Boolean> getClientExpanded();

    @NonNull
    LiveRunnable.Mutable getClientExpandEvent();

    void expandClient();

    void setFileName(String uri);
  }

  interface InteractionListener {

    void onUploadEventClicked();

    void onDocumentEventClicked(Object fileName);
  }
}
