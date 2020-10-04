package com.creations.naina.api;

import android.content.Context;

import androidx.annotation.NonNull;

import com.creations.mvvm.live.MutableLiveData;
import com.creations.naina.models.Config;
import com.experiment.billing.model.components.Configuration;
import com.google.gson.Gson;

import static com.creations.naina.utils.FileUtils.readFromAssets;

public class ConfigurationRepository implements IConfigurationRepository {

  @NonNull
  private final MutableLiveData<Config> mConfig = new MutableLiveData<>();

  public ConfigurationRepository(final Context context,
                                 final Gson gson) {
    Config.IES.setConfiguration(gson.fromJson(readFromAssets(context, "ies.json"), Configuration.class));
    Config.SKS.setConfiguration(gson.fromJson(readFromAssets(context, "sks.json"), Configuration.class));
    updateDefaultConfig();
  }

  @NonNull
  @Override
  public MutableLiveData<Config> getConfig() {
    return mConfig;
  }

  private void updateDefaultConfig() {
    setConfig(Config.fromIndex(0));
  }

  @Override
  public void setConfig(final int index) {
    setConfig(Config.fromIndex(index));
  }

  private void setConfig(@NonNull final Config config) {
    Config currentValue = mConfig.getValue();
    if (currentValue != config) {
      mConfig.postValue(config);
    }
  }

  @Override
  public CharSequence[] getEntries() {
    CharSequence[] sequences = new CharSequence[Config.values().length];
    Config[] values = Config.values();
    for (int i = 0; i < values.length; i++) {
      Config config = values[i];
      sequences[i] = config.getConfiguration().getEntity().getProprietor();
    }
    return sequences;
  }
}
