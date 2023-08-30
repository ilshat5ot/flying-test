package com.gridnine.testing;

import com.gridnine.testing.filters.Filter;
import com.gridnine.testing.flight.Flight;

import java.util.List;

public class DataProcessor {
    private Filter filter;
    private List<Flight> flights;

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public List<Flight> executeFilter() {
        return filter.filter(flights);
    }
}
