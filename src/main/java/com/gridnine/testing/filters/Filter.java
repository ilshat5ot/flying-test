package com.gridnine.testing.filters;

import com.gridnine.testing.flight.Flight;

import java.util.List;

public interface Filter {
    List<Flight> filter(List<Flight> flights);
}
