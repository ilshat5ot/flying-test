package com.gridnine.testing;

import com.gridnine.testing.filters.impl.DepartureAfterCurrentTime;
import com.gridnine.testing.filters.impl.DepartureDateBeforeArrivalDate;
import com.gridnine.testing.filters.impl.TimeOnEarthIsLessThanSpecifiedTime;
import com.gridnine.testing.flight.Flight;
import com.gridnine.testing.flight.Segment;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class TestData {
    List<Flight> flights;
    private DataProcessor dataProcessor;

    @BeforeEach
    public void prepareData() {
        LocalDateTime now = LocalDateTime.now();


//      Нормальный перелет с часом ожидания на земле
        var normalFlight = createFlight(
                now.plusHours(1), now.plusHours(3),
                now.plusHours(4), now.plusHours(7)
        );
//      Уже прошедший перелет
        var pastFlight = createFlight(now.minusDays(2), now.minusDays(2).plusHours(2));
//      Суммарное время ожидания между сегментами больше 2 часов
        var moreTwoHoursOnGround = createFlight(
                now, now.plusHours(2),
                now.plusHours(5), now.plusHours(6),
                now.plusHours(9), now.plusHours(11)
        );
//      Время прибытия до времени отправки
        var errorFlight = createFlight(now.plusHours(10), now.plusHours(5));
        flights = List.of(normalFlight, pastFlight, moreTwoHoursOnGround, errorFlight);

        dataProcessor = new DataProcessor();
    }

    @Test
    void departureAfterCurrentTime() {
        var filter = new DepartureAfterCurrentTime();
        dataProcessor.setFlights(flights);
        dataProcessor.setFilter(filter);

        List<Flight> flightList = dataProcessor.executeFilter();

        Assertions.assertEquals(3, flightList.size());
    }

    @Test
    void departureDateBeforeArrivalDate() {
        var filter = new DepartureDateBeforeArrivalDate();
        dataProcessor.setFlights(flights);
        dataProcessor.setFilter(filter);

        List<Flight> flightList = dataProcessor.executeFilter();

        Assertions.assertEquals(3, flightList.size());
    }

    @Test
    void TimeOnEarthIsLessThanSpecifiedTime() {
        var filter = new TimeOnEarthIsLessThanSpecifiedTime(2);
        dataProcessor.setFlights(flights);
        dataProcessor.setFilter(filter);

        List<Flight> flightList = dataProcessor.executeFilter();

        Assertions.assertEquals(3, flightList.size());
    }

    @Test
    void allFilter() {
        var filter1 = new DepartureAfterCurrentTime();
        var filter2 = new DepartureDateBeforeArrivalDate();
        var filter3 = new TimeOnEarthIsLessThanSpecifiedTime(2);
        dataProcessor.setFlights(flights);
        dataProcessor.setFilter(filter1);


        dataProcessor.setFlights(dataProcessor.executeFilter());
        dataProcessor.setFilter(filter2);

        dataProcessor.setFlights(dataProcessor.executeFilter());
        dataProcessor.setFilter(filter3);

        List<Flight> flightList = dataProcessor.executeFilter();

        Assertions.assertEquals(1, flightList.size());
    }

    private static Flight createFlight(LocalDateTime... dates) {
        List<Segment> segments = new ArrayList<>(dates.length / 2);
        for (int i = 0; i < (dates.length - 1); i += 2) {
            segments.add(new Segment(dates[i], dates[i + 1]));
        }
        return new Flight(segments);
    }
}
