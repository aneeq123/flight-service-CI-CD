package com.tus.flightservice.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tus.flightservice.entity.Flight;
import com.tus.flightservice.service.FlightService;

@WebMvcTest(FlightController.class)
class FlightControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FlightService flightService;

    @Autowired
    private ObjectMapper objectMapper;

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
    void getAllFlights_shouldReturnFlightList() throws Exception {
        when(flightService.getAllFlights()).thenReturn(List.of(flight));

        mockMvc.perform(get("/api/flights"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].flightNumber").value("FR123"))
                .andExpect(jsonPath("$[0].airline").value("Ryanair"));
    }

    @Test
    void getFlightById_shouldReturnFlightWhenFound() throws Exception {
        when(flightService.getFlightById(1L)).thenReturn(Optional.of(flight));

        mockMvc.perform(get("/api/flights/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.flightNumber").value("FR123"))
                .andExpect(jsonPath("$.arrivalCity").value("London"));
    }

    @Test
    void getFlightById_shouldReturnNotFoundWhenMissing() throws Exception {
        when(flightService.getFlightById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/flights/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createFlight_shouldReturnSavedFlight() throws Exception {
        Flight newFlight = new Flight();
        newFlight.setFlightNumber("EI200");
        newFlight.setAirline("Aer Lingus");
        newFlight.setDepartureCity("Dublin");
        newFlight.setArrivalCity("Paris");
        newFlight.setDepartureDate(LocalDate.of(2026, 5, 10));
        newFlight.setDepartureTime(LocalTime.of(14, 45));
        newFlight.setAvailableSeats(150);
        newFlight.setStatus("ON_TIME");

        Flight savedFlight = new Flight();
        savedFlight.setId(2L);
        savedFlight.setFlightNumber("EI200");
        savedFlight.setAirline("Aer Lingus");
        savedFlight.setDepartureCity("Dublin");
        savedFlight.setArrivalCity("Paris");
        savedFlight.setDepartureDate(LocalDate.of(2026, 5, 10));
        savedFlight.setDepartureTime(LocalTime.of(14, 45));
        savedFlight.setAvailableSeats(150);
        savedFlight.setStatus("ON_TIME");

        when(flightService.saveFlight(org.mockito.ArgumentMatchers.any(Flight.class))).thenReturn(savedFlight);

        mockMvc.perform(post("/api/flights")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newFlight)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.flightNumber").value("EI200"))
                .andExpect(jsonPath("$.airline").value("Aer Lingus"));
    }
}