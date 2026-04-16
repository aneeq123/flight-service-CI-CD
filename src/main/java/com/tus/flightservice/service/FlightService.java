package com.tus.flightservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tus.flightservice.entity.Flight;
import com.tus.flightservice.repository.FlightRepository;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public Optional<Flight> getFlightById(Long id) {
        return flightRepository.findById(id);
    }
    
    public Flight saveFlight(Flight flight) {
        return flightRepository.save(flight);
    }
}