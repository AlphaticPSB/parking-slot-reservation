package com.example.parking;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Parking Slot Reservation System API",
                version = "1.0",
                description = "APIs for managing users, slots, bookings in a parking system"
        )
)
@SpringBootApplication
public class ParkingSlotReservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(ParkingSlotReservationApplication.class, args);
    }
}
