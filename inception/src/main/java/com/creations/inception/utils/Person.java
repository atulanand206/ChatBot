package com.creations.inception.utils;

import android.util.Log;

import com.example.application.utils.DateFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Person {

    int duration = 60;
    List<Slot> slots = new ArrayList<>();
    List<Slot> freeSlots = new ArrayList<>();

    Slot slot;

    public Person(Slot slot, String edges) {
        this.slot = slot;
        String[] split = edges.split("['.*']");
        Date[] start = new Date[split.length/4], end = new Date[split.length/4];
        for (int i=0;i<split.length;i++) {
            Date dateFromString = DateFormatter.MILITARY.getDateFromString(split[i]);
            if (dateFromString != null) {
                if ((i-1)%4==0)
                    start[(i-1)/4] = dateFromString;
                else
                    end[(i-1)/4] = dateFromString;
            }
        }
        for (int i=0;i<start.length;i++) {
            slots.add(new Slot(start[i], end[i]));
        }
    }

    public void calculateFreeSlots() {
        freeSlots.clear();
        if (slots.isEmpty()) {
            freeSlots.add(slot.clone());
        }

        int size = slots.size();
        Slot firstSlot = slots.get(0);
        add(slot.start, firstSlot.start);

        for (int i=0;i<slots.size()-1;i++) {
            add(slots.get(i).end, slots.get(i+1).start);
        }

        Slot lastSlot = slots.get(size - 1);
        add(lastSlot.end, slot.end);
    }
    private static final String TAG = Person.class.getSimpleName();

    public void filterDifference(List<Slot> freeSlots2) {
        Log.d(TAG, String.valueOf(freeSlots.size()));
        Log.d(TAG, String.valueOf(freeSlots2.size()));


    }

    private void add(Date start, Date end) {
        if (minutes(start, end) >= duration)
            freeSlots.add(new Slot(start, end));
    }

    private long minutes(Date start, Date end) {
        long diffInMillies = Math.abs(end.getTime() - start.getTime());
        return TimeUnit.MINUTES.convert(diffInMillies, TimeUnit.MILLISECONDS);
    }


}
