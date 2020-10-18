package com.creations.naina.ui.container;

import android.app.Application;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.creations.bang.ui.bang.BangViewModel;
import com.creations.condition.Preconditions;
import com.creations.mvvm.live.LiveEvent;
import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MediatorLiveData;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.naina.models.ContactProps;
import com.creations.mvvm.ui.menu.MenuViewModel;
import com.creations.mvvm.ui.text.TextViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.creations.naina.api.IConfigurationRepository;
import com.creations.naina.models.CanvasP;
import com.creations.naina.ui.contact.ContactAdapter;
import com.creations.naina.ui.contact.ContactContract;
import com.creations.naina.ui.contact.ContactViewModel;
import com.experiment.billing.constants.Constants;
import com.experiment.billing.model.components.Client;
import com.experiment.billing.model.components.Configuration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class ContainerViewModel extends MenuViewModel<CanvasP> implements ContainerContract.ViewModel<CanvasP> {

  //region Variables
  @NonNull
  BangViewModel mBangViewModel;
  @NonNull
  private final ContactViewModel.Factory mContactFactory;
  @NonNull
  IConfigurationRepository mConfigurationRepository;
  @NonNull
  private final LiveEvent.Mutable<Integer> mUploadEvent = new LiveEvent.Mutable<>();
  @NonNull
  private final LiveRunnable.Mutable mEntityExpandEvent = new LiveRunnable.Mutable();
  @NonNull
  private final MutableLiveData<Boolean> mEntityExpanded = new MutableLiveData<>(false);
  @NonNull
  private final LiveRunnable.Mutable mBankExpandEvent = new LiveRunnable.Mutable();
  @NonNull
  private final MutableLiveData<Boolean> mBankExpanded = new MutableLiveData<>(false);
  @NonNull
  private final LiveRunnable.Mutable mRateExpandEvent = new LiveRunnable.Mutable();
  @NonNull
  private final MutableLiveData<Boolean> mRateExpanded = new MutableLiveData<>(false);
  @NonNull
  private final LiveRunnable.Mutable mClientExpandEvent = new LiveRunnable.Mutable();
  @NonNull
  private final MutableLiveData<Boolean> mClientExpanded = new MutableLiveData<>(false);

  @NonNull
  private final LiveEvent.Mutable<String> mDocumentEvent = new LiveEvent.Mutable<>();
  @NonNull
  private final LiveEvent.Mutable<String> mSaveConfigEvent = new LiveEvent.Mutable<>();
  @NonNull
  private final LiveEvent.Mutable<String> mLoadConfigEvent = new LiveEvent.Mutable<>();
  @NonNull
  private final MediatorLiveData<Integer> mConfigurationIndex = new MediatorLiveData<>();
  @NonNull
  private final MutableLiveData<Integer> mFrontLayoutVisibility = new MutableLiveData<>(GONE);
  @NonNull
  private final MutableLiveData<Integer> mMainLayoutVisibility = new MutableLiveData<>(VISIBLE);

  @NonNull
  private final MutableLiveData<CharSequence[]> mEntries = new MutableLiveData<>();

  @NonNull
  private final TextViewModel mEntityName;

  @NonNull
  private final TextViewModel mProprietor;

  @NonNull
  private final TextViewModel mWorkAddress;

  @NonNull
  private final TextViewModel mSiteAddress;

  @NonNull
  private final TextViewModel mGSTIN;

  @NonNull
  private final TextViewModel mMobileNumber;

  @NonNull
  private final TextViewModel mBankDetails;

  @NonNull
  private final TextViewModel mAccountNumber;

  @NonNull
  private final TextViewModel mIfsc;

  @NonNull
  private final TextViewModel mAuthorisedSignatory;

  @NonNull
  private final TextViewModel mGst;

  @NonNull
  private final TextViewModel mSgst;

  @NonNull
  private final TextViewModel mCgst;

  @NonNull
  private final TextViewModel mIgst;

  @NonNull
  private final TextViewModel mRate;

  @NonNull
  private final TextViewModel mStartingInvoiceNumber;

  @NonNull
  private final List<ContactContract.ViewModel> mClients = new ArrayList<>();

  @NonNull
  private final ContactAdapter mContactAdapter = new ContactAdapter(mClients);

  @NonNull
  private final TextViewModel mOutputFileName;

  @NonNull
  private final MutableLiveData<String> mInputFileName = new MutableLiveData<>("");

  @NonNull
  private final MutableLiveData<AdapterView.OnItemSelectedListener> mItemSelectedListener = new MutableLiveData<>();
  private Configuration mConfiguration;
  //endregion

  public ContainerViewModel(@NonNull final Application application,
                            @NonNull final BangViewModel.Factory bangFactory,
                            @NonNull final TextViewModel.Factory factory,
                            @NonNull final ContactViewModel.Factory contactFactory,
                            @NonNull final CanvasP props,
                            @NonNull final IConfigurationRepository configurationRepository) {
    super(application, props);
    mBangViewModel = bangFactory.create();
    mContactFactory = contactFactory;
    mEntityName = factory.create();
    mProprietor = factory.create();
    mWorkAddress = factory.create();
    mSiteAddress = factory.create();
    mGSTIN = factory.create();
    mMobileNumber = factory.create();
    mBankDetails = factory.create();
    mAccountNumber = factory.create();
    mIfsc = factory.create();
    mAuthorisedSignatory = factory.create();
    mGst = factory.create();
    mSgst = factory.create();
    mCgst = factory.create();
    mIgst = factory.create();
    mRate = factory.create();
    mStartingInvoiceNumber = factory.create();
    mOutputFileName = factory.create();
    setTextHeaders();
    addContextCallbacks();
    setClickListeners();
    setVisibility(VISIBLE);
    toggleVisibility(true);
    mConfigurationRepository = configurationRepository;
    mItemSelectedListener.postValue(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        setConfiguration(position);
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    mEntries.postValue(mConfigurationRepository.getEntries());
  }

  private void setConfiguration(int position) {
    mConfigurationIndex.postValue(position);
    mConfigurationRepository.setConfig(position);
    setClients();
    setData();
  }

  private void setClients() {
    mConfiguration = mConfigurationRepository.getConfiguration();
    mContactAdapter.clearItems();
    List<Client> clients = mConfiguration.getClients();
    if (clients != null)
      for (int i = 0; i < clients.size(); i++) {
        Client client = clients.get(i);
        addCrewMember(i, client);
      }
  }

  private void setTextHeaders() {
    mEntityName.setHeader("Name");
    mProprietor.setHeader("Proprietor");
    mWorkAddress.setHeader("Work Address");
    mSiteAddress.setHeader("Site Address");
    mGSTIN.setHeader("GSTIN");
    mGSTIN.setRegex(Constants.REGEX_GSTIN);
    mMobileNumber.setHeader("Mobile Number");
    mMobileNumber.setRegex(Constants.REGEX_MOBILE_NUMBER);
    mBankDetails.setHeader("Bank Name");
    mAccountNumber.setHeader("A/c No");
    mIfsc.setHeader("IFSC");
    mIfsc.setRegex(Constants.REGEX_IFSC);
    mAuthorisedSignatory.setHeader("Authorised Signatory");
    mGst.setHeader("GST");
    mGst.setRegex(Constants.REGEX_DOUBLE_LESS_THAN_100);
    mSgst.setHeader("SGST");
    mSgst.setRegex(Constants.REGEX_DOUBLE_LESS_THAN_100);
    mCgst.setHeader("CGST");
    mCgst.setRegex(Constants.REGEX_DOUBLE_LESS_THAN_100);
    mIgst.setHeader("IGST");
    mIgst.setRegex(Constants.REGEX_DOUBLE_LESS_THAN_100);
    mRate.setHeader("Rate");
    mStartingInvoiceNumber.setHeader("Invoice Begin");
    mStartingInvoiceNumber.setRegex(Constants.REGEX_INTEGER);
    mOutputFileName.setHeader("Output File Name");
    mOutputFileName.setHeaderVisibility(GONE);
  }

  private void addContextCallbacks() {
    mContextCallback.addSource(mEntityName.getContextCallback());
    mContextCallback.addSource(mProprietor.getContextCallback());
    mContextCallback.addSource(mWorkAddress.getContextCallback());
    mContextCallback.addSource(mSiteAddress.getContextCallback());
    mContextCallback.addSource(mGSTIN.getContextCallback());
    mContextCallback.addSource(mMobileNumber.getContextCallback());
    mContextCallback.addSource(mBankDetails.getContextCallback());
    mContextCallback.addSource(mAccountNumber.getContextCallback());
    mContextCallback.addSource(mIfsc.getContextCallback());
    mContextCallback.addSource(mAuthorisedSignatory.getContextCallback());
    mContextCallback.addSource(mGst.getContextCallback());
    mContextCallback.addSource(mSgst.getContextCallback());
    mContextCallback.addSource(mCgst.getContextCallback());
    mContextCallback.addSource(mIgst.getContextCallback());
    mContextCallback.addSource(mRate.getContextCallback());
    mContextCallback.addSource(mStartingInvoiceNumber.getContextCallback());
    mContextCallback.addSource(mOutputFileName.getContextCallback());
  }

  private void setClickListeners() {
    mEntityName.setAfterTextChangedCallback(enteredString -> {
      mConfiguration.getEntity().setFirm(enteredString);
      saveConfiguration();
    });
    mProprietor.setAfterTextChangedCallback(enteredString -> {
      mConfiguration.getEntity().setProprietor(enteredString);
      saveConfiguration();
    });
    mWorkAddress.setAfterTextChangedCallback(enteredString -> {
      mConfiguration.getEntity().setHomeAddress(enteredString);
      saveConfiguration();
    });
    mSiteAddress.setAfterTextChangedCallback(enteredString -> {
      mConfiguration.getEntity().setFirmAddress(enteredString);
      saveConfiguration();
    });
    mGSTIN.setAfterTextChangedCallback(enteredString -> {
      mConfiguration.getEntity().setGstin(enteredString);
      saveConfiguration();
    });
    mMobileNumber.setAfterTextChangedCallback(enteredString -> {
      mConfiguration.getEntity().setMobileNumber(enteredString);
      saveConfiguration();
    });
    mBankDetails.setAfterTextChangedCallback(enteredString -> {
      mConfiguration.getBankDetails().setNameOfBank(enteredString);
      saveConfiguration();
    });
    mAccountNumber.setAfterTextChangedCallback(enteredString -> {
      mConfiguration.getBankDetails().setAccountNumber(enteredString);
      saveConfiguration();
    });
    mIfsc.setAfterTextChangedCallback(enteredString -> {
      mConfiguration.getBankDetails().setIfsc(enteredString);
      saveConfiguration();
    });
    mAuthorisedSignatory.setAfterTextChangedCallback(enteredString -> {
      mConfiguration.setAuthorisedSignatory(enteredString);
      saveConfiguration();
    });
    mStartingInvoiceNumber.setAfterTextChangedCallback(enteredString -> {
      if (enteredString == null)
        return;
      mConfiguration.setInvoiceBegin(Integer.parseInt(enteredString));
      saveConfiguration();
    });
    mGst.setAfterTextChangedCallback(enteredString -> {
      try {
        if (enteredString != null) {
          mConfiguration.getRates().setGstRate(Double.parseDouble(enteredString));
          saveConfiguration();
        }
      } catch (NumberFormatException ignored) {
      }
    });
    mCgst.setAfterTextChangedCallback(enteredString -> {
      try {
        if (enteredString != null) {
          mConfiguration.getRates().setCgstRate(Double.parseDouble(enteredString));
          saveConfiguration();
        }
      } catch (NumberFormatException ignored) {
      }
    });
    mSgst.setAfterTextChangedCallback(enteredString -> {
      try {
        if (enteredString != null) {
          mConfiguration.getRates().setSgstRate(Double.parseDouble(enteredString));
          saveConfiguration();
        }
      } catch (NumberFormatException ignored) {
      }
    });
    mIgst.setAfterTextChangedCallback(enteredString -> {
      try {
        if (enteredString != null) {
          mConfiguration.getRates().setIgstRate(Double.parseDouble(enteredString));
          saveConfiguration();
        }
      } catch (NumberFormatException ignored) {
      }
    });
    mRate.setAfterTextChangedCallback(enteredString -> {
      try {
        if (enteredString != null) {
          mConfiguration.getRates().setRateValue(Double.parseDouble(enteredString));
          saveConfiguration();
        }
      } catch (NumberFormatException ignored) {
      }
    });
  }

  private void saveConfiguration() {
    mConfigurationRepository.setConfig(mConfiguration);
  }

  private void setData() {
    mEntityName.setText(mConfiguration.getEntity().getFirm());
    mProprietor.setText(mConfiguration.getEntity().getProprietor());
    mWorkAddress.setText(mConfiguration.getEntity().getHomeAddress());
    mSiteAddress.setText(mConfiguration.getEntity().getFirmAddress());
    mGSTIN.setText(mConfiguration.getEntity().getGstin());
    mMobileNumber.setText(mConfiguration.getEntity().getMobileNumber());
    mBankDetails.setText(mConfiguration.getBankDetails().getNameOfBank());
    mAccountNumber.setText(mConfiguration.getBankDetails().getAccountNumber());
    mIfsc.setText(mConfiguration.getBankDetails().getIfsc());
    mAuthorisedSignatory.setText(mConfiguration.getAuthorisedSignatory());
    mGst.setText(String.valueOf(mConfiguration.getRates().getGstRate()));
    mCgst.setText(String.valueOf(mConfiguration.getRates().getCgstRate()));
    mSgst.setText(String.valueOf(mConfiguration.getRates().getSgstRate()));
    mIgst.setText(String.valueOf(mConfiguration.getRates().getIgstRate()));
    mRate.setText(String.valueOf(mConfiguration.getRates().getRateValue()));
    mStartingInvoiceNumber.setText(String.valueOf(1));
  }

  @Override
  public void expandEntity() {
    boolean b = mEntityExpanded.getValue().booleanValue();
    mEntityExpanded.postValue(!b);
    mEntityExpandEvent.postEvent();
  }

  @Override
  public void expandBank() {
    boolean b = mBankExpanded.getValue().booleanValue();
    mBankExpanded.postValue(!b);
    mBankExpandEvent.postEvent();
  }

  @Override
  public void expandRate() {
    boolean b = mRateExpanded.getValue().booleanValue();
    mRateExpanded.postValue(!b);
    mRateExpandEvent.postEvent();
  }

  @Override
  public void expandClient() {
    boolean b = mClientExpanded.getValue().booleanValue();
    mClientExpanded.postValue(!b);
    mClientExpandEvent.postEvent();
  }

  private void addCrewMember(int i, @NonNull Client observer) {
    ContactProps observerProps = new ContactProps(observer.getClient(), observer.getId(), observer.getGstin());
    addCrewMember(i, observerProps);
  }

  private void addCrewMember(int i, @NonNull ContactProps observerProps) {
    ContactViewModel contactViewModel = mContactFactory.create();
    contactViewModel.postContactProps(observerProps, enteredString -> {
      try {
        if (enteredString != null) {
          mConfiguration.getClients().get(i).setGstin(enteredString);
          ContainerViewModel.this.saveConfiguration();
        }
      } catch (NumberFormatException ignored) {
      }
    });
    mContextCallback.addSource(contactViewModel.getContextCallback());
    mContactAdapter.addContactItem(contactViewModel);
  }

  private void refreshOutputFileName() {
    mOutputFileName.setHeaderVisibility(VISIBLE);
    mOutputFileName.setText(getOutputFileNameText());
  }

  private String getOutputFileNameText() {
    Object value = mEntityName.getText().getValue();
    return String.format("%s-%s", value == null || value.equals("") ? "Doc" : value.toString(),
            new SimpleDateFormat("dd-MMM-yyyy-hh-mm-ss").format(Calendar.getInstance().getTime())).replace("/", "").replace(" ", "-");
  }

  @Override
  public void onUploadClicked() {
    Object obj = mStartingInvoiceNumber.getText().getValue();
    if (obj == null) {
      mUploadEvent.postEvent(1);
      return;
    }
    String value = obj.toString();
    int invoice = Integer.parseInt(value);
    mUploadEvent.postEvent(invoice);
  }

  @Override
  public void onDocumentClicked() {
    Object value = mOutputFileName.getText().getValue();
    mDocumentEvent.postEvent(value != null ? value.toString() : "");
  }

  @Override
  public void onSaveConfig() {
    mSaveConfigEvent.postEvent("");
  }

  @Override
  public void onLoadConfig() {
    mLoadConfigEvent.postEvent("");
  }

  @Override
  public void setFileName(String uri) {
    mInputFileName.setValue(uri);
    setClients();
    refreshOutputFileName();
  }

  @Override
  public void toggleVisibility(final boolean mainLayoutVisibility) {
    setFrontLayoutVisibility(mainLayoutVisibility ? GONE : VISIBLE);
    setMainLayoutVisibility(mainLayoutVisibility ? VISIBLE : GONE);
  }
  //region Getters
  @NonNull
  @Override
  public LiveData<Integer> getFrontLayoutVisibility() {
    return mFrontLayoutVisibility;
  }

  @Override
  public void setFrontLayoutVisibility(final int frontLayoutVisibility) {
    mFrontLayoutVisibility.setValue(frontLayoutVisibility);
  }

  @NonNull
  @Override
  public LiveData<Integer> getMainLayoutVisibility() {
    return mMainLayoutVisibility;
  }

  @Override
  public void setMainLayoutVisibility(final int mainLayoutVisibility) {
    mMainLayoutVisibility.setValue(mainLayoutVisibility);
  }

  @NonNull
  @Override
  public TextViewModel getEntityNameViewModel() {
    return mEntityName;
  }

  @NonNull
  @Override
  public TextViewModel getProprietor() {
    return mProprietor;
  }

  @NonNull
  @Override
  public TextViewModel getWorkAddress() {
    return mWorkAddress;
  }

  @NonNull
  @Override
  public TextViewModel getSiteAddress() {
    return mSiteAddress;
  }

  @NonNull
  @Override
  public TextViewModel getGstIn() {
    return mGSTIN;
  }

  @NonNull
  @Override
  public TextViewModel getMobileNumber() {
    return mMobileNumber;
  }

  @NonNull
  @Override
  public TextViewModel getBankDetails() {
    return mBankDetails;
  }

  @NonNull
  @Override
  public TextViewModel getAccountNumber() {
    return mAccountNumber;
  }

  @NonNull
  @Override
  public TextViewModel getIfsc() {
    return mIfsc;
  }

  @NonNull
  @Override
  public TextViewModel getAuthorisedSignatory() {
    return mAuthorisedSignatory;
  }

  @NonNull
  @Override
  public TextViewModel getGst() {
    return mGst;
  }

  @NonNull
  @Override
  public TextViewModel getCgst() {
    return mCgst;
  }

  @NonNull
  @Override
  public TextViewModel getSgst() {
    return mSgst;
  }

  @NonNull
  @Override
  public TextViewModel getIgst() {
    return mIgst;
  }

  @NonNull
  @Override
  public TextViewModel getRate() {
    return mRate;
  }

  @NonNull
  @Override
  public TextViewModel getStartingInvoiceNumber() {
    return mStartingInvoiceNumber;
  }

  @NonNull
  @Override
  public MutableLiveData<String> getInputFileName() {
    return mInputFileName;
  }

  @NonNull
  @Override
  public TextViewModel getOutputFileName() {
    return mOutputFileName;
  }

  @NonNull
  @Override
  public LiveEvent.Mutable<Integer> getUploadEvent() {
    return mUploadEvent;
  }

  @NonNull
  @Override
  public LiveEvent.Mutable<String> getDocumentEvent() {
    return mDocumentEvent;
  }

  @NonNull
  @Override
  public LiveEvent.Mutable<String> getSaveConfigEvent() {
    return mSaveConfigEvent;
  }

  @NonNull
  @Override
  public LiveEvent.Mutable<String> getLoadConfigEvent() {
    return mLoadConfigEvent;
  }

  @NonNull
  @Override
  public MutableLiveData<Boolean> getEntityExpanded() {
    return mEntityExpanded;
  }

  @NonNull
  @Override
  public LiveRunnable.Mutable getEntityExpandEvent() {
    return mEntityExpandEvent;
  }

  @NonNull
  @Override
  public MutableLiveData<Boolean> getBankExpanded() {
    return mBankExpanded;
  }

  @NonNull
  @Override
  public LiveRunnable.Mutable getBankExpandEvent() {
    return mBankExpandEvent;
  }

  @NonNull
  @Override
  public MutableLiveData<Boolean> getRateExpanded() {
    return mRateExpanded;
  }

  @NonNull
  @Override
  public LiveRunnable.Mutable getRateExpandEvent() {
    return mRateExpandEvent;
  }

  @NonNull
  @Override
  public MutableLiveData<Boolean> getClientExpanded() {
    return mClientExpanded;
  }

  @NonNull
  @Override
  public LiveRunnable.Mutable getClientExpandEvent() {
    return mClientExpandEvent;
  }

  @NonNull
  @Override
  public ContactAdapter getContactAdapter() {
    return mContactAdapter;
  }

  @NonNull
  @Override
  public MutableLiveData<Integer> getConfigurationIndex() {
    return mConfigurationIndex;
  }

  @NonNull
  @Override
  public MutableLiveData<CharSequence[]> getEntries() {
    return mEntries;
  }

  @NonNull
  @Override
  public MutableLiveData<AdapterView.OnItemSelectedListener> getItemSelectedListener() {
    return mItemSelectedListener;
  }

  @NonNull
  @Override
  public BangViewModel getBangViewModel() {
    return mBangViewModel;
  }
  //endregion

  public static class Factory extends MVVMViewModel.Factory<ContainerViewModel> {

    @NonNull
    private final CanvasP mProps;

    @NonNull
    private final TextViewModel.Factory mFactory;

    @NonNull
    private final ContactViewModel.Factory mContactFactory;

    @NonNull
    private final BangViewModel.Factory mBangFactory;

    @NonNull
    private final IConfigurationRepository mConfigurationRepository;

    public Factory(@NonNull final Application application,
                   @NonNull final BangViewModel.Factory bangFactory,
                   @NonNull final TextViewModel.Factory factory,
                   @NonNull final ContactViewModel.Factory contactFactory,
                   @NonNull final CanvasP props,
                   @NonNull final IConfigurationRepository configurationRepository) {
      super(ContainerViewModel.class, application);
      mBangFactory = Preconditions.requiresNonNull(bangFactory, "BangFactory");
      mContactFactory = Preconditions.requiresNonNull(contactFactory, "ContactFactory");
      mFactory = Preconditions.requiresNonNull(factory, "Factory");
      mProps = Preconditions.requiresNonNull(props, "Props");
      mConfigurationRepository = Preconditions.requiresNonNull(configurationRepository, "ConfigurationRepo");
    }

    @NonNull
    @Override
    public ContainerViewModel create() {
      return new ContainerViewModel(mApplication, mBangFactory, mFactory, mContactFactory, mProps, mConfigurationRepository);
    }
  }
}
