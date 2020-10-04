package com.creations.naina.ui.container;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.creations.bang.ui.bang.BangViewModel;
import com.creations.condition.Preconditions;
import com.creations.mvvm.live.LiveRunnable;
import com.creations.mvvm.live.MediatorLiveData;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.ui.menu.MenuViewModel;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.creations.naina.api.IConfigurationRepository;
import com.creations.naina.models.CanvasP;
import com.creations.naina.models.Config;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

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
  private final MutableLiveData<AdapterView.OnItemSelectedListener> mItemSelectedListener = new MutableLiveData<>();

  public ContainerViewModel(@NonNull final Application application,
                            @NonNull final BangViewModel.Factory bangFactory,
                            @NonNull final CanvasP props,
                            @NonNull final IConfigurationRepository configurationRepository) {
    super(application, props);
    mBangViewModel = bangFactory.create();
    setVisibility(VISIBLE);
    mConfigurationRepository = configurationRepository;
    mItemSelectedListener.postValue(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        mConfigurationIndex.postValue(position);
        mConfigurationRepository.setConfig(position);
        Config config = mConfigurationRepository.getConfig().getValue();
        if (config != null)
          Log.d("CVM", config.getConfiguration().getEntity().getProprietor());
      }

      @Override
      public void onNothingSelected(AdapterView<?> parent) {

      }
    });
    mEntries.postValue(configurationRepository.getEntries());
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
    private final BangViewModel.Factory mBangFactory;

    @NonNull
    private final IConfigurationRepository mConfigurationRepository;

    public Factory(@NonNull final Application application,
                   @NonNull final BangViewModel.Factory bangFactory,
                   @NonNull final CanvasP props,
                   @NonNull final IConfigurationRepository configurationRepository) {
      super(ContainerViewModel.class, application);
      mBangFactory = Preconditions.requiresNonNull(bangFactory, "BangFactory");
      mProps = Preconditions.requiresNonNull(props, "Props");
      mConfigurationRepository = Preconditions.requiresNonNull(configurationRepository, "ConfigurationRepo");
    }

    @NonNull
    @Override
    public ContainerViewModel create() {
      return new ContainerViewModel(mApplication, mBangFactory, mProps, mConfigurationRepository);
    }
  }
}
