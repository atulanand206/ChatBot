package com.creations.naina.services;

import android.content.Context;

import com.creations.naina.models.Config;
import com.example.application.utils.SharedPreferenceHelper;
import com.experiment.billing.model.components.Configuration;
import com.google.gson.Gson;

import static com.creations.naina.utils.FileUtils.readFromAssets;

public class SessionContext {

  private final SharedPreferenceHelper mSharedPreferenceHelper;
  private final Gson mGson;
  private final Context mContext;

  private static final String CONFIG = "Config";
  private static final String CONFIG_INDEX = "Config_index";

  private final Config mConfig = new Config();

  public SessionContext(final SharedPreferenceHelper sharedPreferenceHelper,
                        final Context context,
                        final Gson gson) {
    this.mSharedPreferenceHelper = sharedPreferenceHelper;
    this.mGson = gson;
    this.mContext = context;
    initConfig();
    updateFromSharedPrefs();
  }

  public void initConfig() {
    mConfig.addConfiguration(mGson.fromJson(readFromAssets(mContext, "ies.json"), Configuration.class));
    mConfig.addConfiguration(mGson.fromJson(readFromAssets(mContext, "sks.json"), Configuration.class));
    mConfig.setSelectedIndex(0);
    clearClients(mConfig);
  }

  private void clearClients(Config config) {
    config.getConfigurations().forEach(configuration -> {
      if (configuration.getClients()!=null)
        configuration.getClients().clear();
    });
  }

  public Config getConfig() {
    return mConfig;
  }

  public void setConfigurationIndex(final int selectedIndex) {
    mConfig.setSelectedIndex(selectedIndex);
    saveConfiguration();
  }

  public void setConfiguration(final Config selectedConfig) {
    mConfig.setConfig(selectedConfig);
    saveConfiguration();
  }

  public void setConfiguration(Configuration config) {
    mConfig.setConfiguration(config);
    saveConfiguration();
  }

  public void saveConfiguration() {
    mSharedPreferenceHelper.save(CONFIG, mGson.toJson(mConfig));
  }

  public void updateFromSharedPrefs() {
    Config config = mGson.fromJson(mSharedPreferenceHelper.getString(CONFIG), Config.class);
    if (config!=null && !config.getConfigurations().isEmpty()) {
//      clearClients(config);
      setConfiguration(config);
    }
  }
}
