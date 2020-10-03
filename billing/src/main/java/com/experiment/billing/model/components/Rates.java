package com.experiment.billing.model.components;

import com.google.gson.annotations.SerializedName;

public class Rates {

  public static final String GST_RATE = "gst_rate";
  public static final String SGST_RATE = "sgst_rate";
  public static final String CGST_RATE = "cgst_rate";
  public static final String IGST_RATE = "igst_rate";
  public static final String RATE_VALUE = "rate_value";
  public static final String RATE_UNIT = "rate_unit";
  public static final String QUANTITY_UNIT = "quantity_unit";

  @SerializedName(GST_RATE)
  private double gstRate;

  @SerializedName(SGST_RATE)
  private double sgstRate;

  @SerializedName(CGST_RATE)
  private double cgstRate;

  @SerializedName(IGST_RATE)
  private double igstRate;

  @SerializedName(RATE_VALUE)
  private double rateValue;

  @SerializedName(RATE_UNIT)
  private String rateUnit;

  @SerializedName(QUANTITY_UNIT)
  private String quantityUnit;

  public double getGstRate() {
    return gstRate;
  }

  public double getSgstRate() {
    return sgstRate;
  }

  public double getCgstRate() {
    return cgstRate;
  }

  public double getIgstRate() {
    return igstRate;
  }

  public double getRateValue() {
    return rateValue;
  }

  public String getRateUnit() {
    return rateUnit;
  }

  public String getQuantityUnit() {
    return quantityUnit;
  }
}
