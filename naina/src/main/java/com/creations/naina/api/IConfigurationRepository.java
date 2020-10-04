package com.creations.naina.api;

import com.experiment.billing.model.components.Configuration;

public interface IConfigurationRepository {

  void setConfig(Configuration config);

  void setConfig(int index);

  CharSequence[] getEntries();

  Configuration getConfiguration();
}
