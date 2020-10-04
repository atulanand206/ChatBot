package com.experiment.billing.model.components;

import com.google.gson.annotations.SerializedName;

public class Entity {

  public static final String GSTIN = "gstin";
  public static final String MOBILE_NUMBER = "mobile_number";
  public static final String FIRM = "firm";
  public static final String PROPRIETOR = "proprietor";
  public static final String HOME_ADDRESS = "home_address";
  public static final String FIRM_ADDRESS = "firm_address";
  public static final String FROM = "from";

  @SerializedName(GSTIN)
  private String gstin;

  @SerializedName(MOBILE_NUMBER)
  private String mobileNumber;

  @SerializedName(FIRM)
  private String firm;

  @SerializedName(PROPRIETOR)
  private String proprietor;

  @SerializedName(HOME_ADDRESS)
  private String homeAddress;

  @SerializedName(FIRM_ADDRESS)
  private String firmAddress;

  @SerializedName(FROM)
  private String from;

  public String getGstin() {
    return gstin;
  }

  public String getMobileNumber() {
    return mobileNumber;
  }

  public String getFirm() {
    return firm;
  }

  public String getProprietor() {
    return proprietor;
  }

  public String getHomeAddress() {
    return homeAddress;
  }

  public String getFirmAddress() {
    return firmAddress;
  }

  public String getFrom() {
    return from;
  }

  public void setGstin(String gstin) {
    this.gstin = gstin;
  }

  public void setMobileNumber(String mobileNumber) {
    this.mobileNumber = mobileNumber;
  }

  public void setFirm(String firm) {
    this.firm = firm;
  }

  public void setProprietor(String proprietor) {
    this.proprietor = proprietor;
  }

  public void setHomeAddress(String homeAddress) {
    this.homeAddress = homeAddress;
  }

  public void setFirmAddress(String firmAddress) {
    this.firmAddress = firmAddress;
  }

  public void setFrom(String from) {
    this.from = from;
  }
}
