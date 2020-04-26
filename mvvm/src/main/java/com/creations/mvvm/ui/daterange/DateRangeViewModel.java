package com.creations.mvvm.ui.daterange;

import android.app.Application;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

import com.creations.condition.Preconditions;
import com.creations.mvvm.live.MediatorLiveData;
import com.creations.mvvm.live.MutableLiveData;
import com.creations.mvvm.models.props.DateRangeProps;
import com.creations.mvvm.ui.FormViewModelBase;
import com.creations.mvvm.utils.TransformationsUtils;
import com.creations.mvvm.viewmodel.MVVMViewModel;
import com.example.application.utils.DateFormatter;

import java.util.Calendar;
import java.util.Date;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

/**
 * This ViewModel works with a DateRange and is to be used for creating forms.
 */
public class DateRangeViewModel extends FormViewModelBase implements DateRangeContract.ViewModel {

    @NonNull
    private final MutableLiveData<Date> mStart = new MutableLiveData<>(Calendar.getInstance().getTime());
    @NonNull
    private final MediatorLiveData<Date> mEnd = new MediatorLiveData<>(Calendar.getInstance().getTime());
    @NonNull
    private final LiveData<String> mStartString = TransformationsUtils.mapNonNullSource(mStart, DateFormatter.START_END_ADVISORY::getDateForUi);
    private final LiveData<String> mEndString = TransformationsUtils.mapNonNullSource(mEnd, DateFormatter.START_END_ADVISORY::getDateForUi);

    public DateRangeViewModel(@NonNull final Application application,
                              @NonNull final DateRangeProps dateRangeProps) {
        super(application, "DateRange items");
        Preconditions.requiresNonNull(dateRangeProps, "DateRangeProps");
        mEnd.addSource(mStart, start -> {
            Date end = mEnd.getValue();
            Calendar bound = Calendar.getInstance();
            if (start != null) {
                bound.setTime(start);
            }
            if (end == null || bound.getTime().compareTo(end) >= 0) {
                bound.add(Calendar.MINUTE, 0);
                mEnd.postValue(bound.getTime());
            }
        });
        postProps(dateRangeProps);
    }

    @Override
    public void postProps(@NonNull final DateRangeProps dateRangeProps) {
        Preconditions.requiresNonNull(dateRangeProps, "DateRangeProps");
        mStart.setValue(dateRangeProps.getStartDate());
        mEnd.setValue(dateRangeProps.getEndDate());
    }

    @NonNull
    @Override
    public LiveData<String> getStartString() {
        return mStartString;
    }
    @NonNull
    @Override
    public LiveData<String> getEndString() {
        return mEndString;
    }
    @NonNull
    @Override
    public LiveData<Date> getStart() {
        return mStart;
    }
    @NonNull
    @Override
    public LiveData<Date> getEnd() {
        return mEnd;
    }

    @Override
    public void pickEndTime() {
        Boolean disabled = mDisabled.getValue();
        if (disabled != null && !disabled) {
            mContextCallback.postEvent(context -> showTimePicker(mEnd, context, mStart.getValue()));
        }
    }

    /**
     * Called to show date time pickers for start time.
     */
    @Override
    public void pickStartTime() {
        Boolean disabled = mDisabled.getValue();
        if (disabled != null && !disabled) {
            mContextCallback.postEvent(context -> showTimePicker(mStart, context, null));
        }
    }

    private void showTimePicker(@NonNull final MutableLiveData<Date> dateData,
                                @NonNull final Context context, final @Nullable Date minimum) {
        TimePickerDialog timeDialog;
        DatePickerDialog dateDialog;

        Date date = dateData.getValue();
        if (date == null) {
            date = Calendar.getInstance().getTime();
            mStart.setValue(date);
        }
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);
        //noinspection deprecation
        timeDialog = new TimePickerDialog(context, TimePickerDialog.THEME_DEVICE_DEFAULT_DARK,
                (view, hourOfDay, minute) -> {
                    Date dateValue = dateData.getValue();
                    if (dateValue == null) {
                        dateValue = Calendar.getInstance().getTime();
                    }
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dateValue);
                    cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    cal.set(Calendar.MINUTE, minute);
                    cal.set(Calendar.SECOND, 0);
                    Date newDate = cal.getTime();
                    if (minimum != null && minimum.compareTo(newDate) > 0) {
                        Calendar minCal = Calendar.getInstance();
                        minCal.setTime(minimum);
                        minCal.add(Calendar.MINUTE, 1);
                        dateData.postValue(minCal.getTime());
                    } else {
                        dateData.postValue(newDate);
                    }
                }, dateCal.get(Calendar.HOUR_OF_DAY), dateCal.get(Calendar.MINUTE), false);
        //noinspection deprecation
        dateDialog = new DatePickerDialog(context, DatePickerDialog.THEME_DEVICE_DEFAULT_DARK,
                (view, year, month, dayOfMonth) -> {
                    Date timeValue = mStart.getValue();
                    if (timeValue == null) {
                        timeValue = Calendar.getInstance().getTime();
                    }
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(timeValue);
                    cal.set(Calendar.YEAR, year);
                    cal.set(Calendar.MONTH, month);
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    dateData.setValue(cal.getTime());
                    timeDialog.show();

                }, dateCal.get(Calendar.YEAR), dateCal.get(Calendar.MONTH), dateCal.get(Calendar.DAY_OF_MONTH));

        DatePicker datePicker = dateDialog.getDatePicker();
        if (minimum != null) {
            Calendar minCalendar = Calendar.getInstance();
            minCalendar.setTime(minimum);
            datePicker.setMinDate(minCalendar.getTimeInMillis());
        }

        dateDialog.show();
    }

    public static class Factory extends MVVMViewModel.Factory<DateRangeViewModel> {

        @NonNull
        private final DateRangeProps mDateRangeProps;

        public Factory(@NonNull final Application application,
                       @NonNull final DateRangeProps dateRangeProps) {
            super(DateRangeViewModel.class, application);
            mDateRangeProps = Preconditions.requiresNonNull(dateRangeProps, "DateRangeProps");
        }

        @NonNull
        @Override
        public DateRangeViewModel create() {
            return new DateRangeViewModel(mApplication, mDateRangeProps);
        }
    }
}
