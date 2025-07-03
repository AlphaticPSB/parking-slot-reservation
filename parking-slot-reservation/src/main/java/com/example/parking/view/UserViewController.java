package com.example.parking.view;

import com.example.parking.model.Booking;
import com.example.parking.model.Slot;
import com.example.parking.model.User;
import com.example.parking.repository.BookingRepository;
import com.example.parking.repository.SlotRepository;
import com.example.parking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/user")
public class UserViewController {

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/dashboard")
    public String userDashboard(Model model) {
        model.addAttribute("slots", slotRepository.findAll());
        return "user-dashboard";
    }

    @PostMapping("/reserve/{id}")
    public String reserveSlot(@PathVariable Long id,
                              @AuthenticationPrincipal UserDetails userDetails) {
        Slot slot = slotRepository.findById(id).orElse(null);
        if (slot != null && slot.isAvailable()) {
            User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
            if (user != null) {
                // Update slot status
                slot.setAvailable(false);
                slot.setReservedBy(user);
                slotRepository.save(slot);

                // Create and save booking
                Booking booking = new Booking();
                booking.setSlot(slot);
                booking.setUser(user);
                booking.setBookingTime(LocalDateTime.now());
                bookingRepository.save(booking);
            }
        }
        return "redirect:/user/dashboard";
    }

    @PostMapping("/cancel-booking/{id}")
    public String cancelUserBooking(@PathVariable Long id,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        Slot slot = slotRepository.findById(id).orElse(null);
        if (slot != null && !slot.isAvailable()) {
            User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
            if (user != null && user.equals(slot.getReservedBy())) {
                // Delete the user's booking
                bookingRepository.deleteByUserAndSlot(user, slot);

                // Make slot available again
                slot.setAvailable(true);
                slot.setReservedBy(null);
                slotRepository.save(slot);
            }
        }
        return "redirect:/user/dashboard";
    }
}