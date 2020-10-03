package com.experiment.billing.model.components;

import com.google.gson.annotations.SerializedName;

public class Target {

  public static final String BILL_TO = "bill_to";
  public static final String ADDRESS = "address";
  public static final String GSTIN = "gstin";
  public static final String STATE_NAME = "state_name";
  public static final String STATE_CODE = "state_code";

  @SerializedName(BILL_TO)
  private String billTo;

  @SerializedName(ADDRESS)
  private String address;

  @SerializedName(GSTIN)
  private String gstin;

  @SerializedName(STATE_NAME)
  private String stateName;

  @SerializedName(STATE_CODE)
  private int stateCode;

  public Target(
      final String billTo,
      final String address,
      final String gstin,
      final String stateName,
      final int stateCode) {
    this.billTo = billTo;
    this.address = address;
    this.gstin = gstin;
    this.stateName = stateName;
    this.stateCode = stateCode;
  }

  public String getBillTo() {
    return billTo;
  }

  public String getAddress() {
    return address;
  }

  public String getGstin() {
    return gstin;
  }

  public String getStateName() {
    return stateName;
  }

  public int getStateCode() {
    return stateCode;
  }
}
