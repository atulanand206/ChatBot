package com.creations.naina.api;

import android.content.Context;

import androidx.annotation.NonNull;

import com.creations.mvvm.live.MutableLiveData;
import com.creations.naina.models.Config;
import com.example.application.utils.SharedPreferenceHelper;
import com.experiment.billing.model.components.Configuration;
import com.google.gson.Gson;

import static com.creations.naina.utils.FileUtils.readFromAssets;

public class ConfigurationRepository implements IConfigurationRepository {

  private final SharedPreferenceHelper mSharedPreferenceHelper;
  private final Gson mGson;
  @NonNull
  private final MutableLiveData<Config> mConfig = new MutableLiveData<>();

  public ConfigurationRepository(final SharedPreferenceHelper sharedPreferenceHelper,
                                 final Context context,
                                 final Gson gson) {
    mSharedPreferenceHelper = sharedPreferenceHelper;
    mGson = gson;
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
    String configString = mSharedPreferenceHelper.getString("Config");
    int configIndex = mSharedPreferenceHelper.getInt("ConfigIndex");
    Config config;
    if (configString != null) {
      config = Config.fromIndex(configIndex);
      Configuration configuration = mGson.fromJson(configString, Configuration.class);
      config.setConfiguration(configuration);
    }
    else
      config = Config.fromIndex(0);
    setConfig(config);
  }

  @Override
  public void setConfig(final Configuration config) {
    Config configValue = mConfig.getValue();
    if (configValue != null) {
      configValue.setConfiguration(config);
      setConfig(configValue);
    }
  }

  @Override
  public void setConfig(final int index) {
    setConfig(Config.fromIndex(index));
  }

  private void setConfig(@NonNull final Config config) {
    mConfig.postValue(config);
    Config currentValue = mConfig.getValue();
    if (currentValue != null) {
      mSharedPreferenceHelper.save("Config", mGson.toJson(config.getConfiguration()));
      mSharedPreferenceHelper.save("ConfigIndex", config.getIndex());
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
