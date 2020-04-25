package com.creations.mvvm.models.tooltip;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class EpochDate implements Serializable {

    @SerializedName("nano")
    private long nano;

    @SerializedName("epochSecond")
    private long epochSecond;

    public long getNano() {
        return nano;
    }

    public void setNano(long nano) {
        this.nano = nano;
    }

    public long getEpochSecond() {
        return epochSecond;
    }

    public void setEpochSecond(long epochSecond) {
        this.epochSecond = epochSecond;
    }
}
