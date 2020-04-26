package com.creations.inception.utils;

import java.util.ArrayList;
import java.util.List;

public class Mock {

    List<Slot> slots1 = new ArrayList<>();
    List<Slot> slots2 = new ArrayList<>();

    List<String> inOfc1 = new ArrayList<>();
    List<String> inOfc2 = new ArrayList<>();

    public Mock() {
        Slot slot1 = new Slot("['9:00', '20:00']");
        Slot slot2 = new Slot("['10:00', '18:30']");
        Person person1 = new Person(slot1, "[['9:00', '10:30'],['12:00', '13:00'],['16:00', '18:00']");
        Person person2 = new Person(slot2,"[['10:00', '11:30'],['12:30', '14:30'],['14:30', '15:00'],['16:00', '17:00']");

        person1.calculateFreeSlots();
        person2.calculateFreeSlots();

        person1.filterDifference(person2.freeSlots);

    }
}
