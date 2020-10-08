package com.creations.naina.models;

import com.creations.mvvm.models.props.Props;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class ContactProps extends Props implements Serializable {

  @NonNull
  private String mName;
  @NonNull
  private String mId;
  @NonNull
  private String mGstin;

  public ContactProps(@NonNull String client, @NonNull String id, @NonNull String gstin) {
    mName = client;
    mId = id;
    mGstin = gstin;
  }

  @NonNull
  public String getName() {
    return mName;
  }

  @NonNull
  public String getId() {
    return mId;
  }

  @NonNull
  public String getGstin() {
    return mGstin;
  }


}
