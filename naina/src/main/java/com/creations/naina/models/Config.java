package com.creations.naina.models;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.experiment.billing.model.components.Configuration;

public enum Config {

  IES(0),
  SKS(1);

  @IntRange(
          from = 0L,
          to = 1L
  )
  private int index;
  private Configuration configuration;

  Config(int index) {
    this.index = index;
  }

  public int getIndex() {
    return index;
  }

  public Configuration getConfiguration() {
    return configuration;
  }

  public void setConfiguration(Configuration configuration) {
    this.configuration = configuration;
  }

  @NonNull
  public static Config fromIndex(@IntRange(from = 0L, to = 1L) int index) {
    Config[] var1 = values();
    int var2 = var1.length;

    for (int var3 = 0; var3 < var2; ++var3) {
      Config config = var1[var3];
      if (config.getIndex() == index) {
        return config;
      }
    }

    throw new IllegalArgumentException("index was not valid");
  }

  @NonNull
  public String getString() {
    if (configuration != null) {
      return configuration.getEntity().getProprietor();
    } else {
      throw new RuntimeException("MapViewNamesContainsIndex");
    }
  }
}
