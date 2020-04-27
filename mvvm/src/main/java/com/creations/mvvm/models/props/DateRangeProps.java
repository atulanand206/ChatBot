package com.creations.mvvm.models.props;

import com.creations.condition.Preconditions;
import com.example.application.utils.DateFormatter;

import java.io.Serializable;
import java.util.Date;

import androidx.annotation.NonNull;

public class DateRangeProps extends Props implements Serializable {

    private Date startDate;

    private Date endDate;
    private boolean disabled = false;

    public DateRangeProps(final boolean pDisabled) {
        disabled = pDisabled;
    }

    public DateRangeProps(@NonNull final Date pStartDate,
                          @NonNull final Date pEndDate) {
        startDate = Preconditions.requiresNonNull(pStartDate, "StartDate");
        endDate = Preconditions.requiresNonNull(pEndDate, "EndDate");
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(@NonNull final Date pStartDate) {
        startDate = Preconditions.requiresNonNull(pStartDate, "StartDate");
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(@NonNull final Date pEndDate) {
        endDate = Preconditions.requiresNonNull(pEndDate, "EndDate");
    }

    public String getStart() {
        return DateFormatter.START_END_ADVISORY.getDateForUi(startDate);
    }

    public String getEnd() {
        return DateFormatter.START_END_ADVISORY.getDateForUi(endDate);
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(final boolean pDisabled) {
        disabled = pDisabled;
    }
}
