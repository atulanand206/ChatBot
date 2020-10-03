package com.experiment.billing.model.components;

import com.google.gson.annotations.SerializedName;

public class Configuration {

  public static final String OUTPUT_DATE_FORMAT = "dd/MM/yyyy";
  public static final String TIME_ZONE = "IST";

  public static final String ENTITY = "entity";
  public static final String RATES = "rates";
  public static final String VALUATION = "valuation";
  public static final String BANK_DETAILS = "bank_details";
  public static final String AUTHORISED_SIGNATORY = "authorised_signatory";

  @SerializedName(ENTITY)
  private Entity entity;

  @SerializedName(RATES)
  private Rates rates;

  @SerializedName(BANK_DETAILS)
  private Bank bankDetails;

  @SerializedName(AUTHORISED_SIGNATORY)
  private String authorisedSignatory;

  public Entity getEntity() {
    return entity;
  }

  public Rates getRates() {
    return rates;
  }

  public Bank getBankDetails() {
    return bankDetails;
  }

  public String getAuthorisedSignatory() {
    return authorisedSignatory;
  }
}
