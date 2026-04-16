package com.tus.flightservice;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.tus.flightservice.entity.Flight;
import com.tus.flightservice.repository.FlightRepository;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner loadData(FlightRepository flightRepository) {
        return args -> {

            Flight f1 = new Flight();
            f1.setFlightNumber("FR123");
            f1.setAirline("Ryanair");
            f1.setDepartureCity("Dublin");
            f1.setArrivalCity("London");
            f1.setDepartureDate(LocalDate.now());
            f1.setDepartureTime(LocalTime.of(10, 30));
            f1.setAvailableSeats(120);
            f1.setStatus("ON_TIME");

            Flight f2 = new Flight();
            f2.setFlightNumber("EI456");
            f2.setAirline("Aer Lingus");
            f2.setDepartureCity("Dublin");
            f2.setArrivalCity("Paris");
            f2.setDepartureDate(LocalDate.now().plusDays(1));
            f2.setDepartureTime(LocalTime.of(14, 15));
            f2.setAvailableSeats(95);
            f2.setStatus("DELAYED");

            flightRepository.save(f1);
            flightRepository.save(f2);
        };
    }
}