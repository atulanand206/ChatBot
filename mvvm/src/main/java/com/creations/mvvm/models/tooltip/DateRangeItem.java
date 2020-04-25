package com.creations.mvvm.models.tooltip;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Objects;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;


public class DateRangeItem {
    @NonNull
    @SerializedName("start_time")
    private final Date startDate;

    @NonNull
    @SerializedName("end_time")
    private final Date endDate;

    @ColorRes
    private int backgroundColorId;

    public DateRangeItem(@NonNull final Date startDate,
                         @NonNull final Date endDate,
                         @ColorRes final int backgroundColorId) {
        this.startDate = Objects.requireNonNull(startDate, "StartDate");
        this.endDate = Objects.requireNonNull(endDate, "EndDate");
        this.backgroundColorId = backgroundColorId;
    }

    @NonNull
    public Date getStartDate() {
        return startDate;
    }

    @NonNull
    public Date getEndDate() {
        return endDate;
    }

    @ColorRes
    public int backgroundColor() {
        return backgroundColorId;
    }

}
