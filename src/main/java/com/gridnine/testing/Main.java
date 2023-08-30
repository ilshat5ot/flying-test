package com.gridnine.testing;

import com.gridnine.testing.filters.impl.DepartureAfterCurrentTime;
import com.gridnine.testing.filters.impl.DepartureDateBeforeArrivalDate;
import com.gridnine.testing.filters.impl.TimeOnEarthIsLessThanSpecifiedTime;
import com.gridnine.testing.flight.Flight;
import com.gridnine.testing.flight.FlightBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Flight> flights = FlightBuilder.createFlights();
        printFlightList(flights);

        DataProcessor processor = new DataProcessor();

        System.out.println("Исключены из списка перелеты в которых есть сегменты время отправки после текущего времени:");
        processor.setFlights(flights);
        processor.setFilter(new DepartureAfterCurrentTime());
        List<Flight> flights1 = processor.executeFilter();
        printFlightList(flights1);

        System.out.println("Исключены из списка перелеты в которых есть сегменты время прибытия до времени отправки:");
        processor.setFilter(new DepartureDateBeforeArrivalDate());
        List<Flight> flights2 = processor.executeFilter();
        printFlightList(flights2);

        System.out.println("Исключены из списка перелеты в которых общее время простоя на земле больше заданного количества часов");
        processor.setFilter(new TimeOnEarthIsLessThanSpecifiedTime(2));
        List<Flight> flights3 = processor.executeFilter();
        printFlightList(flights3);

    }

    public static void printFlightList(List<Flight> list) {
        list.forEach(System.out::println);
        System.out.println("=================================");
    }
}
