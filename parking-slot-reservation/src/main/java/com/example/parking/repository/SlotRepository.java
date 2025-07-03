package com.example.parking.repository;

import com.example.parking.model.Slot;
import com.example.parking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SlotRepository extends JpaRepository<Slot, Long> {

    // ✅ Fetch only available slots
    List<Slot> findByAvailableTrue();

    // ✅ Fetch all slots reserved by a specific user
    List<Slot> findByReservedBy(User user);

    // ✅ Fetch slot by slot number (for duplicate check)
    Optional<Slot> findBySlotNumber(String slotNumber);
}