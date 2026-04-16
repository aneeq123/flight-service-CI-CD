package com.tus.flightservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tus.flightservice.entity.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {

}