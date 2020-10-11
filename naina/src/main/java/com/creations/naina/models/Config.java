package com.creations.naina.models;

import com.experiment.billing.model.components.Configuration;

import java.util.ArrayList;
import java.util.List;

public class Config {

  private List<Configuration> mConfigurations = new ArrayList<>();

  private int selectedIndex;

  public void addConfiguration(final Configuration configuration) {
    mConfigurations.add(configuration);
  }

  public List<Configuration> getConfigurations() {
    return mConfigurations;
  }

  public int getSelectedIndex() {
    return selectedIndex;
  }

  public void setSelectedIndex(int selectedIndex) {
    this.selectedIndex = selectedIndex;
  }

  public void setConfiguration(final Configuration configuration) {
    mConfigurations.set(selectedIndex, configuration);
  }

  public void setConfig(Config config) {
    mConfigurations.clear();
    mConfigurations.addAll(config.getConfigurations());
    selectedIndex = config.getSelectedIndex();
  }

  public CharSequence[] entries() {
    CharSequence[] sequences = new CharSequence[mConfigurations.size()];
    for (int i = 0; i < mConfigurations.size(); i++) {
      Configuration configuration = mConfigurations.get(i);
      sequences[i] = configuration.getEntity().getFirm();
    }
    return sequences;
  }

  public Configuration getConfiguration() {
    return mConfigurations.get(selectedIndex);
  }

}
