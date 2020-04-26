package com.creations.inception.utils;

import com.example.application.utils.DateFormatter;

import java.util.Date;

public class Slot {

//    "['9:00', '20:00']"

    Date start;

    Date end;


    public Slot(String edges) {
        String[] split = edges.split(",");
        start = DateFormatter.MILITARY.getDateFromString(split[0]
                .replace("['", "")
                .replace("'", "")
        );
        end = DateFormatter.MILITARY.getDateFromString(split[1]
                .replace("']", "")
                .replace("'", "")
        );
    }

    public Slot(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    @Override
    protected Slot clone() {
        return new Slot(((Date) start.clone()), ((Date) end.clone()));
    }
}
