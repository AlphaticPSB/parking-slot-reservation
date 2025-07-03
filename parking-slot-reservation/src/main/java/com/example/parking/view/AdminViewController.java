package com.example.parking.view;

import com.example.parking.model.Slot;
import com.example.parking.model.User;
import com.example.parking.model.Booking;
import com.example.parking.repository.BookingRepository;
import com.example.parking.repository.SlotRepository;
import com.example.parking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Admin dashboard: show slots and bookings
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/dashboard")
    public String adminDashboard(Model model,
                                 @RequestParam(value = "error", required = false) String error) {
        List<Slot> slots = slotRepository.findAll();
        List<Booking> bookings = bookingRepository.findAll();

        model.addAttribute("slots", slots);
        model.addAttribute("bookings", bookings);
        model.addAttribute("slot", new Slot());

        if (error != null) {
            model.addAttribute("error", error);
        }

        return "admin-dashboard";
    }

    // ✅ Create a new slot with duplicate check
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/add-slot")
    public String addSlot(@RequestParam("slotNumber") String slotNumber) {
        Optional<Slot> existing = slotRepository.findBySlotNumber(slotNumber);
        if (existing.isPresent()) {
            return "redirect:/admin/dashboard?error=Slot number '" + slotNumber + "' already exists!";
        }

        Slot slot = new Slot();
        slot.setSlotNumber(slotNumber);
        slot.setAvailable(true);
        slotRepository.save(slot);

        return "redirect:/admin/dashboard";
    }

    // ✅ Cancel all bookings
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cancel-all-bookings")
    public String cancelAllBookings() {
        bookingRepository.deleteAll();

        for (Slot slot : slotRepository.findAll()) {
            slot.setAvailable(true);
            slot.setReservedBy(null);
            slotRepository.save(slot);
        }

        return "redirect:/admin/dashboard";
    }

    // ✅ Cancel bookings for a specific user (also deletes bookings)
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/cancel-user-bookings")
    @Transactional
    public String cancelBookingsByUser(@RequestParam("username") String username) {
        var userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();

            // Step 1: Delete bookings from DB
            bookingRepository.deleteByUser(user);

            // Step 2: Mark user’s slots as available
            List<Slot> userSlots = slotRepository.findByReservedBy(user);
            for (Slot slot : userSlots) {
                slot.setAvailable(true);
                slot.setReservedBy(null);
            }
            slotRepository.saveAll(userSlots);
        }

        return "redirect:/admin/dashboard";
    }
}