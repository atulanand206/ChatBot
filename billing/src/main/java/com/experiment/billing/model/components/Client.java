package com.experiment.billing.model.components;

public class Client {

  public static final String CLIENT = "client";
  public static final String GSTIN = "gstin";

  private String client;
  private String id;
  private String gstin;

  public Client(String client, String id, String gstin) {
    this.client = client;
    this.id = id;
    this.gstin = gstin;
  }

  public String getClient() {
    return client;
  }

  public void setClient(String client) {
    this.client = client;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getGstin() {
    return gstin;
  }

  public void setGstin(String gstin) {
    this.gstin = gstin;
  }
}
