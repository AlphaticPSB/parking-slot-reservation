package com.example.parking.controller;

import com.example.parking.dto.UserDto;
import com.example.parking.dto.response.ApiResponse;
import com.example.parking.model.User;
import com.example.parking.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<User>> registerUser(@Valid @RequestBody UserDto userDto) {
        User createdUser = userService.registerUser(userDto);
        return ResponseEntity.ok(ApiResponse.success("✅ User registered", createdUser));
    }
}
