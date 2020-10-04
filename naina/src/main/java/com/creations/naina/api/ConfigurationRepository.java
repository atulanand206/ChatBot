package com.creations.naina.api;

import com.creations.naina.services.SessionContext;
import com.experiment.billing.model.components.Configuration;

public class ConfigurationRepository implements IConfigurationRepository {

  private final SessionContext mSessionContext;

  public ConfigurationRepository(final SessionContext sessionContext) {
    mSessionContext = sessionContext;
  }

  @Override
  public void setConfig(final Configuration config) {
    mSessionContext.setConfiguration(config);
  }

  @Override
  public void setConfig(final int index) {
    mSessionContext.setConfigurationIndex(index);
  }

  @Override
  public Configuration getConfiguration() {
    return mSessionContext.getConfig().getConfiguration();
  }

  @Override
  public CharSequence[] getEntries() {
    return mSessionContext.getConfig().entries();
  }
}
