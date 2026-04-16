package com.tus.flightservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.tus.flightservice.entity.Flight;
import com.tus.flightservice.repository.FlightRepository;

@ExtendWith(MockitoExtension.class)
class FlightServiceTest {

    @Mock
    private FlightRepository flightRepository;

    @InjectMocks
    private FlightService flightService;

    private Flight flight;

    @BeforeEach
    void setUp() {
        flight = new Flight();
        flight.setId(1L);
        flight.setFlightNumber("FR123");
        flight.setAirline("Ryanair");
        flight.setDepartureCity("Dublin");
        flight.setArrivalCity("London");
        flight.setDepartureDate(LocalDate.of(2026, 4, 20));
        flight.setDepartureTime(LocalTime.of(10, 30));
        flight.setAvailableSeats(120);
        flight.setStatus("ON_TIME");
    }

    @Test
    void getAllFlights_shouldReturnFlightList() {
        when(flightRepository.findAll()).thenReturn(List.of(flight));

        List<Flight> flights = flightService.getAllFlights();

        assertEquals(1, flights.size());
        assertEquals("FR123", flights.get(0).getFlightNumber());
        verify(flightRepository, times(1)).findAll();
    }

    @Test
    void getFlightById_shouldReturnFlightWhenFound() {
        when(flightRepository.findById(1L)).thenReturn(Optional.of(flight));

        Optional<Flight> result = flightService.getFlightById(1L);

        assertTrue(result.isPresent());
        assertEquals("Ryanair", result.get().getAirline());
        verify(flightRepository, times(1)).findById(1L);
    }

    @Test
    void saveFlight_shouldReturnSavedFlight() {
        when(flightRepository.save(flight)).thenReturn(flight);

        Flight savedFlight = flightService.saveFlight(flight);

        assertEquals("FR123", savedFlight.getFlightNumber());
        assertEquals("London", savedFlight.getArrivalCity());
        verify(flightRepository, times(1)).save(flight);
    }
}