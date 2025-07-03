package com.example.parking.repository;

import com.example.parking.model.Booking;
import com.example.parking.model.User;
import com.example.parking.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Transactional
    void deleteByUser(User user);

    @Transactional
    void deleteByUserAndSlot(User user, Slot slot); // ✅ Required for user-side cancel
}