package com.example.parking.controller;

import com.example.parking.dto.UserDto;
import com.example.parking.dto.LoginDto;
import com.example.parking.dto.response.ApiResponse;
import com.example.parking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/register", produces = "application/json")
    public ResponseEntity<ApiResponse<Object>> registerUser(@RequestBody UserDto userDto) {
        try {
            userService.registerUser(userDto);
            return ResponseEntity.ok(ApiResponse.success("User registered successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Registration failed: " + e.getMessage(), null));
        }
    }

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<ApiResponse<Object>> loginUser(@RequestBody LoginDto loginDto) {
        System.out.println("🔐 Attempting login for username: " + loginDto.getUsername());
        System.out.println("🔐 Provided raw password: " + loginDto.getPassword());

        boolean loginSuccessful = userService.authenticate(loginDto.getUsername(), loginDto.getPassword());

        if (loginSuccessful) {
            System.out.println("✅ Login successful for: " + loginDto.getUsername());
            return ResponseEntity.ok(ApiResponse.success("Login successful", null));
        } else {
            System.out.println("❌ Invalid credentials for: " + loginDto.getUsername());
            return ResponseEntity.status(401).body(ApiResponse.error("Invalid credentials", null));
        }
    }
}
