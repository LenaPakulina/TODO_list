package ru.job4j.controller.utils;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;

@Component
public class TimeZoneStorage {
    private final List<TimeZoneElement> timeZoneElements = new ArrayList<>();

    public TimeZoneStorage() {
        var zones = new ArrayList<TimeZone>();
        for (String timeId : TimeZone.getAvailableIDs()) {
            zones.add(TimeZone.getTimeZone(timeId));
        }
        for (TimeZone zone : zones) {
            timeZoneElements.add(new TimeZoneElement(zone.getID(), zone.getDisplayName()));
        }
        timeZoneElements.sort(Comparator.comparing(TimeZoneElement::displayName));
    }

    public List<TimeZoneElement> getTimeZoneElements() {
        return timeZoneElements;
    }

    public TimeZone getDefaultParams() {
        return TimeZone.getDefault();
    }

    public record TimeZoneElement(String id, String displayName) { }
}
