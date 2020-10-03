package com.experiment.billing.model.components;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Particular {

  public static final String DATE = "date";
  public static final String VEHICLE_NUMBER = "vehicle_number";
  public static final String PERMIT_NUMBER = "permit_number";
  public static final String HSN_CODE = "hsn_code";
  public static final String QUANTITY_VALUE = "quantity_value";
  public static final String TARGET = "target";
  public static final String TRIP_DETAILS = "trip_details";

  @SerializedName(DATE)
//  @JsonFormat(pattern = INPUT_DATE_FORMAT, timezone = TIME_ZONE)
  private Date date;

  @SerializedName(VEHICLE_NUMBER)
  private String vehicleNumber;

  @SerializedName(PERMIT_NUMBER)
  private String permitNumber;

  @SerializedName(HSN_CODE)
  private String hsnCode;

  @SerializedName(QUANTITY_VALUE)
  private int quantityValue;

  private String destination;

  @SerializedName(TARGET)
  private Target target;

  @SerializedName(TRIP_DETAILS)
  private Trip tripDetails;

  public Particular(
      final Date date,
      final String vehicleNumber,
      final String permitNumber,
      final String hsnCode,
      final int quantityValue,
      final String destination,
      final Target target,
      final Trip tripDetails) {
    this.date = date;
    this.vehicleNumber = vehicleNumber;
    this.permitNumber = permitNumber;
    this.hsnCode = hsnCode;
    this.quantityValue = quantityValue;
    this.destination = destination;
    this.target = target;
    this.tripDetails = tripDetails;
  }

  public Date getDate() {
    return date;
  }

  public String getVehicleNumber() {
    return vehicleNumber;
  }

  public String getPermitNumber() {
    return permitNumber;
  }

  public String getHsnCode() {
    return hsnCode;
  }

  public int getQuantityValue() {
    return quantityValue;
  }

  public String getDestination() {
    return destination;
  }

  public Target getTarget() {
    return target;
  }

  public Trip getTripDetails() {
    return tripDetails;
  }

  @Override
  public String toString() {
    return "Particular{" +
        "date=" + date +
        ", vehicleNumber='" + vehicleNumber + '\'' +
        ", permitNumber='" + permitNumber + '\'' +
        ", hsnCode='" + hsnCode + '\'' +
        ", quantityValue=" + quantityValue +
        ", destination='" + destination + '\'' +
        '}';
  }
}
