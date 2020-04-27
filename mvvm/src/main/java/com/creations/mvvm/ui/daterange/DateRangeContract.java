package com.creations.mvvm.ui.daterange;

import com.creations.mvvm.models.props.DateRangeProps;
import com.creations.mvvm.models.props.Props;
import com.creations.mvvm.ui.IFormViewModelBase;

import java.util.Date;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

public interface DateRangeContract {

    interface ViewModel extends IFormViewModelBase<Props> {

        void postProps(@NonNull DateRangeProps dateRangeProps);

        /**
         * @return liveData corresponding to the start time string.
         */
        @NonNull
        LiveData<String> getStartString();
        /**
         * @return liveData corresponding to the end time string.
         */
        @NonNull
        LiveData<String> getEndString();
        /**
         * @return liveData corresponding to the start time.
         */
        @NonNull
        LiveData<Date> getStart();
        /**
         * @return liveData corresponding to the end time.
         */
        @NonNull
        LiveData<Date> getEnd();

        /**
         * This is called when start time is clicked.
         */
        void pickStartTime();

        /**
         * This is called when end time is clicked.
         */
        void pickEndTime();
    }

}
