package com.example.parking.service;

import com.example.parking.model.Booking;
import java.util.List;

public interface BookingService {
    Booking createBooking(Long slotId, String username);
    List<Booking> getAllBookings();
}
