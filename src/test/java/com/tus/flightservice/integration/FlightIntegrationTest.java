package com.tus.flightservice.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.tus.flightservice.entity.Flight;
import com.tus.flightservice.repository.FlightRepository;

@SpringBootTest
@AutoConfigureMockMvc
class FlightIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FlightRepository flightRepository;

    private Flight savedFlight;

    @BeforeEach
    void setUp() {
        flightRepository.deleteAll();

        Flight flight = new Flight();
        flight.setFlightNumber("EI200");
        flight.setAirline("Aer Lingus");
        flight.setDepartureCity("Dublin");
        flight.setArrivalCity("London");
        flight.setDepartureDate(LocalDate.of(2026, 5, 10));
        flight.setDepartureTime(LocalTime.of(9, 30));
        flight.setAvailableSeats(120);
        flight.setStatus("Scheduled");

        savedFlight = flightRepository.save(flight);
    }

    @Test
    void shouldReturnFlightFromDatabase() throws Exception {
        mockMvc.perform(get("/api/flights/" + savedFlight.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(savedFlight.getId()))
                .andExpect(jsonPath("$.flightNumber").value("EI200"))
                .andExpect(jsonPath("$.airline").value("Aer Lingus"))
                .andExpect(jsonPath("$.departureCity").value("Dublin"))
                .andExpect(jsonPath("$.arrivalCity").value("London"))
                .andExpect(jsonPath("$.availableSeats").value(120))
                .andExpect(jsonPath("$.status").value("Scheduled"));
    }
}