package com.experiment.billing.model.components;

import com.google.gson.annotations.SerializedName;

public class Trip {

  public static final String FROM = "from";
  public static final String TO = "to";
  public static final String LORRY_NUMBER = "lorry_number";
  public static final String DRIVER_NAME_AND_ADDRESS = "driver_name_and_address";
  public static final String DRIVER_MOBILE_NUMBER = "driver_mobile_number";

  @SerializedName(FROM)
  private String from;

  @SerializedName(TO)
  private String to;

  @SerializedName(LORRY_NUMBER)
  private String lorryNumber;

  @SerializedName(DRIVER_NAME_AND_ADDRESS)
  private String driverNameAndAddress;

  @SerializedName(DRIVER_MOBILE_NUMBER)
  private String driverMobileNumber;

  public Trip(final String from, final String to) {
    this.from = from;
    this.to = to;
  }

  public String getFrom() {
    return from;
  }

  public String getTo() {
    return to;
  }

  public String getLorryNumber() {
    return lorryNumber;
  }

  public String getDriverNameAndAddress() {
    return driverNameAndAddress;
  }

  public String getDriverMobileNumber() {
    return driverMobileNumber;
  }
}
