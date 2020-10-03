package com.experiment.billing.model.components;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Bill {

  public static final String CONFIGURATION = "configuration";
  public static final String PAGES = "pages";

  @SerializedName(CONFIGURATION)
  private Configuration fConfiguration;

  @SerializedName(PAGES)
  private List<Page> fPages;

  public com.experiment.billing.model.components.Configuration getConfiguration() {
    return fConfiguration;
  }

  public List<Page> getPages() {
    return fPages;
  }
}
