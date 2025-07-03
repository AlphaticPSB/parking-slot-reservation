package com.example.parking.service;

import com.example.parking.model.Booking;
import com.example.parking.model.Slot;
import com.example.parking.repository.SlotRepository;
import com.example.parking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SlotService {

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private BookingService bookingService;

    public List<Slot> getAvailableSlots() {
        return slotRepository.findByAvailableTrue();
    }

    public Slot createSlot(Slot slot) {
        slot.setAvailable(true);
        return slotRepository.save(slot);
    }

    public Slot reserveSlot(Long slotId, String username) {
        Booking booking = bookingService.createBooking(slotId, username);
        return booking.getSlot();
    }
}
