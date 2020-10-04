package com.creations.naina.ui.container;

import android.app.Application;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;

import com.creations.bang.ui.bang.BangViewModel;
import com.creations.condition.Preconditions;
import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MediatorLiveData;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.ui.menu.MenuViewModel;
import com.creations.mvvm.ui.text.TextViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.creations.naina.api.IConfigurationRepository;
import com.creations.naina.models.CanvasP;
import com.experiment.billing.model.components.Configuration;

import static android.view.View.VISIBLE;

/**
 * This ViewModel works with a TextInputLayout and is to be used for creating forms.
 */
public class ContainerViewModel extends MenuViewModel<CanvasP> implements ContainerContract.ViewModel<CanvasP> {

  @NonNull
  BangViewModel mBangViewModel;

  @NonNull
  IConfigurationRepository mConfigurationRepository;
  @NonNull
  private final LiveRunnable.Mutable mUploadEvent = new LiveRunnable.Mutable();

  @NonNull
  private final LiveRunnable.Mutable mDocumentEvent = new LiveRunnable.Mutable();
  @NonNull
  private final MediatorLiveData<Integer> mConfigurationIndex = new MediatorLiveData<>();

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
  private final MutableLiveData<AdapterView.OnItemSelectedListener> mItemSelectedListener = new MutableLiveData<>();
  private Configuration mConfiguration;

  public ContainerViewModel(@NonNull final Application application,
                            @NonNull final BangViewModel.Factory bangFactory,
                            @NonNull final TextViewModel.Factory factory,
                            @NonNull final CanvasP props,
                            @NonNull final IConfigurationRepository configurationRepository) {
    super(application, props);
    mBangViewModel = bangFactory.create();
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
    setTextHeaders();
    addContextCallbacks();
    setClickListeners();
    setVisibility(VISIBLE);
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
    mConfiguration = mConfigurationRepository.getConfiguration();
    setData();
  }

  private void setTextHeaders() {
    mEntityName.setHeader("Name");
    mProprietor.setHeader("Proprietor");
    mWorkAddress.setHeader("Work Address");
    mSiteAddress.setHeader("Site Address");
    mGSTIN.setHeader("GSTIN");
    mMobileNumber.setHeader("Mobile Number");
    mBankDetails.setHeader("Bank Name");
    mAccountNumber.setHeader("A/c No");
    mIfsc.setHeader("IFSC");
    mAuthorisedSignatory.setHeader("Authorised Signatory");
    mGst.setHeader("GST");
    mSgst.setHeader("SGST");
    mCgst.setHeader("CGST");
    mIgst.setHeader("IGST");
    mRate.setHeader("Rate");
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
      mConfiguration.getEntity().setProprietor(enteredString);
      saveConfiguration();
    });
    mSiteAddress.setAfterTextChangedCallback(enteredString -> {
      mConfiguration.getEntity().setProprietor(enteredString);
      saveConfiguration();
    });
    mGSTIN.setAfterTextChangedCallback(enteredString -> {
      mConfiguration.getEntity().setProprietor(enteredString);
      saveConfiguration();
    });
    mMobileNumber.setAfterTextChangedCallback(enteredString -> {
      mConfiguration.getEntity().setProprietor(enteredString);
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
  public LiveRunnable getUploadEvent() {
    return mUploadEvent;
  }

  @Override
  public void onUploadClicked() {
    mUploadEvent.postEvent();
  }

  @NonNull
  @Override
  public LiveRunnable.Mutable getDocumentEvent() {
    return mDocumentEvent;
  }

  @Override
  public void onDocumentClicked() {
    mDocumentEvent.postEvent();
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

  public static class Factory extends MVVMViewModel.Factory<ContainerViewModel> {

    @NonNull
    private final CanvasP mProps;

    @NonNull
    private final TextViewModel.Factory mFactory;

    @NonNull
    private final BangViewModel.Factory mBangFactory;

    @NonNull
    private final IConfigurationRepository mConfigurationRepository;

    public Factory(@NonNull final Application application,
                   @NonNull final BangViewModel.Factory bangFactory,
                   @NonNull final TextViewModel.Factory factory,
                   @NonNull final CanvasP props,
                   @NonNull final IConfigurationRepository configurationRepository) {
      super(ContainerViewModel.class, application);
      mBangFactory = Preconditions.requiresNonNull(bangFactory, "BangFactory");
      mFactory = Preconditions.requiresNonNull(factory, "Factory");
      mProps = Preconditions.requiresNonNull(props, "Props");
      mConfigurationRepository = Preconditions.requiresNonNull(configurationRepository, "ConfigurationRepo");
    }

    @NonNull
    @Override
    public ContainerViewModel create() {
      return new ContainerViewModel(mApplication, mBangFactory, mFactory, mProps, mConfigurationRepository);
    }
  }
}
