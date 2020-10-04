package com.experiment.billing.model.components;

import com.google.gson.annotations.SerializedName;

public class Bank {

  private static final String NAME_OF_BANK = "name_of_bank";
  private static final String ACCOUNT_NUMBER = "account_number";
  private static final String IFSC = "ifsc";

  @SerializedName(NAME_OF_BANK)
  private String nameOfBank;

  @SerializedName(ACCOUNT_NUMBER)
  private String accountNumber;

  @SerializedName(IFSC)
  private String ifsc;

  public String getNameOfBank() {
    return nameOfBank;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public String getIfsc() {
    return ifsc;
  }

  public void setNameOfBank(String nameOfBank) {
    this.nameOfBank = nameOfBank;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public void setIfsc(String ifsc) {
    this.ifsc = ifsc;
  }
}
