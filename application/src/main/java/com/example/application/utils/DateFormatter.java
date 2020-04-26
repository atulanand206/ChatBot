package com.example.application.utils;


import com.creations.condition.Preconditions;

import org.apache.commons.text.WordUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public enum DateFormatter {

    START_TIME("MM/dd/yy, hh:mm a", true),
    END_TIME("MM/dd/yy, hh:mm a", true),
    START_END_ADVISORY("MMM dd, yyyy hh:mm a"),
    SEASON_DATE("MM/dd/yy"),
    SEASON_TIME("MM/dd/yy, hh:mm a"),
    LAST_UPDATED("MM/dd/yy, hh:mm a", true),
    MAPBOX_V1("EEE MMM dd HH:mm:ss ZZZ yyyy"),
    MAPBOX_V2("yyyy-MM-dd'T'HH:mm:ss.SSSZZZ"),
    MAPBOX_V3("yyyy-MM-dd'T'HH:mm:ss'Z'"),
    INVITATION_DATE_TIME("MMM dd, yyyy, hh:mm a"),
    INVITATION_DATE("MMM dd, yyyy"),
    CERTIFICATE_ISSUE_DATE("yyyy-MM-dd"),
    STREAM_START("hh:mm"),
    MILITARY("HH:mm");

    private boolean showTimeZone = false;

    @NonNull
    private SimpleDateFormat simpleDateFormat;

    DateFormatter(@NonNull final String dateFormat) {
        this.simpleDateFormat = new  SimpleDateFormat(dateFormat, Locale.getDefault());
    }

    DateFormatter(@NonNull final String dateFormat, final boolean timeZone) {
        this(dateFormat);
        this.showTimeZone = timeZone;
    }

    @NonNull
    public String getDateForUi(@Nullable final Date date) {
        if(date == null) {
            return "";
        }
        return getDateForUi(date, 0, 0);
    }

    @NonNull
    public String getDateForUi(@NonNull final Date date, final double lat, final double lon) {
        Calendar instance = Calendar.getInstance();
        if (lat != 0 && lon != 0) {
            TimeZone timeZone = TimeZone.getTimeZone(getEventTimeZone(lat, lon, 3));
            instance.setTimeZone(timeZone);
            simpleDateFormat.setTimeZone(timeZone);
        } else {
            TimeZone timeZone = TimeZone.getDefault();
            instance.setTimeZone(timeZone);
            simpleDateFormat.setTimeZone(timeZone);
        }
        String dateString = simpleDateFormat.format(Preconditions.requiresNonNull(date, "Date"));
        if (showTimeZone) {
            String timeZone = WordUtils.initials(instance.getTimeZone().getDisplayName());
            return String.format("%s %s", dateString, timeZone);
        }
        return dateString;
    }

    @Nullable
    public Date getDateFromString(@NonNull final String dateString) {
        try {
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return simpleDateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @NonNull
    public static String getEventTimeZone(final double lat, final double lon, final int chars) {
        Calendar instance = Calendar.getInstance(TimeZone.getDefault());
        String timeZone = TimezoneMapper.latLngToTimezoneString(lat, lon);
        instance.setTimeZone(TimeZone.getTimeZone(timeZone));
        String initials = WordUtils.initials(instance.getTimeZone().getDisplayName());
        if (chars == 3) {
            return initials;
        } else if (chars == 2) {
            //Todo: this is allowed only for regions with 2 lettered timeZones.
            return initials.charAt(0) + "" + initials.charAt(2);
        }
        return "";
    }

    @Nullable
    public static DateFormatter getFromAvailableFormatters(@NonNull final String dateString) {
        for (DateFormatter formatter : DateFormatter.values()) {
            if (formatter.getDateFromString(dateString) != null)
                return formatter;
        }
        return null;
    }
}
