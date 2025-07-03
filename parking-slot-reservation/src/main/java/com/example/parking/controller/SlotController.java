package com.example.parking.controller;

import com.example.parking.dto.response.ApiResponse;
import com.example.parking.model.Slot;
import com.example.parking.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/slots")
public class SlotController {

    @Autowired
    private SlotService slotService;

    // ✅ Get all available slots
    @GetMapping("/available")
    public ResponseEntity<ApiResponse<List<Slot>>> getAvailableSlots() {
        List<Slot> slots = slotService.getAvailableSlots();
        return ResponseEntity.ok(ApiResponse.success("Available slots fetched successfully", slots));
    }

    // ✅ Reserve a slot by ID (authenticated user)
    @PutMapping("/reserve/{id}")
    public ResponseEntity<ApiResponse<Slot>> reserveSlot(@PathVariable Long id, Principal principal) {
        Slot reservedSlot = slotService.reserveSlot(id, principal.getName());
        return ResponseEntity.ok(ApiResponse.success("Slot reserved successfully", reservedSlot));
    }

    // ✅ Create a new slot (admin only)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Slot>> createSlot(@RequestBody Slot slot) {
        Slot newSlot = slotService.createSlot(slot);
        return ResponseEntity.ok(ApiResponse.success("Slot created successfully", newSlot));
    }
}
