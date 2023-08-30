package com.gridnine.testing.filters.impl;

import com.gridnine.testing.filters.Filter;
import com.gridnine.testing.flight.Flight;

import java.time.LocalDateTime;
import java.util.List;

public class DepartureAfterCurrentTime implements Filter {
    @Override
    public List<Flight> filter(List<Flight> flights) {
        return flights.stream()
                .filter(flight -> flight.getSegments()
                        .stream()
                        .anyMatch(segment -> segment.getDepartureDate().isAfter(LocalDateTime.now())))
                .toList();
    }
}
