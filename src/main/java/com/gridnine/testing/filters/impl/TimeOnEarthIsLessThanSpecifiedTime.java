package com.gridnine.testing.filters.impl;

import com.gridnine.testing.filters.Filter;
import com.gridnine.testing.flight.Flight;
import com.gridnine.testing.flight.Segment;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class TimeOnEarthIsLessThanSpecifiedTime implements Filter {
    private int hours;

    public TimeOnEarthIsLessThanSpecifiedTime(int hours) {
        this.hours = hours;
    }

    @Override
    public List<Flight> filter(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> {
                    LocalDateTime arrivalDate = null;
                    int timeOnGround = 0;
                    for (Segment segment : flight.getSegments()) {
                        if (arrivalDate != null) {
                            timeOnGround += (int) Duration.between(arrivalDate, segment.getDepartureDate()).toHours();
                        }
                        arrivalDate = segment.getArrivalDate();
                    }
                    return timeOnGround < hours;
                })
                .toList();
    }
}
