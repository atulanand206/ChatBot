package com.creations.naina.api;

import androidx.annotation.NonNull;

import com.creations.mvvm.live.MutableLiveData;
import com.creations.naina.models.Config;

public interface IConfigurationRepository {
  @NonNull
  MutableLiveData<Config> getConfig();

  void setConfig(int index);

  CharSequence[] getEntries();
}
