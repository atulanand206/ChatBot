package com.creations.blogger.model;


import com.google.gson.annotations.SerializedName;

public final class APIResponseBody {
  @SerializedName("success")
  private final boolean success;

  @SerializedName("error")
  private final String error;

  @SerializedName("message")
  private final String message;

  @SerializedName("status")
  private final int status;

  @SerializedName("path")
  private final String path;

  public APIResponseBody(
      final boolean success,
      final String error,
      final String message,
      final int status,
      final String path) {
    this.success = success;
    this.error = error;
    this.message = message;
    this.status = status;
    this.path = path;
  }

  public APIResponseBody() {
    this(false, "", "", 0, "");
  }

  public boolean isSuccess() {
    return success;
  }

  public String getError() {
    return error;
  }

  public String getMessage() {
    return message;
  }

  public int getStatus() {
    return status;
  }

  public String getPath() {
    return path;
  }
}
