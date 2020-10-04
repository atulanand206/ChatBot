package com.creations.naina.api;

import androidx.annotation.NonNull;

import com.creations.mvvm.live.MutableLiveData;
import com.creations.naina.models.Config;
import com.experiment.billing.model.components.Configuration;

public interface IConfigurationRepository {
  @NonNull
  MutableLiveData<Config> getConfig();

  void setConfig(Configuration config);

  void setConfig(int index);

  CharSequence[] getEntries();
}
