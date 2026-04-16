package com.tus.flightservice.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.tus.flightservice.entity.Flight;
import com.tus.flightservice.service.FlightService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping("/api/flights")
    public List<Flight> getAllFlights() {
        return flightService.getAllFlights();
    }

    @GetMapping("/api/flights/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Optional<Flight> flight = flightService.getFlightById(id);

        if (flight.isPresent()) {
            return ResponseEntity.ok(flight.get());
        }

        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/api/flights")
    public Flight createFlight(@RequestBody Flight flight) {
        return flightService.saveFlight(flight);
    }
    
    
}