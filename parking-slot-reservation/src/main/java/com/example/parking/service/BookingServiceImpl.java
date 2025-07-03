package com.example.parking.service;

import com.example.parking.model.Booking;
import com.example.parking.model.Slot;
import com.example.parking.model.User;
import com.example.parking.repository.BookingRepository;
import com.example.parking.repository.SlotRepository;
import com.example.parking.repository.UserRepository;
import com.example.parking.service.BookingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SlotRepository slotRepository;

    @Override
    @Transactional
    public Booking createBooking(Long slotId, String username) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Slot slot = slotRepository.findById(slotId)
            .orElseThrow(() -> new RuntimeException("Slot not found"));

        if (!slot.isAvailable()) {
            throw new RuntimeException("Slot already reserved");
        }

        slot.setAvailable(false);
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setSlot(slot);
        booking.setBookingTime(LocalDateTime.now());

        return bookingRepository.save(booking);
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
