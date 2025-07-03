package com.example.parking.service;

import com.example.parking.dto.UserDto;
import com.example.parking.model.User;
import com.example.parking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Register New User using DTO
    public User registerUser(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword())); // Hashed password
        user.setRole("USER");
        user.setEnabled(true); // ✅ Enable user account

        return userRepository.save(user);
    }

    // Login (Authenticate) User
    public boolean authenticate(String username, String rawPassword) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        System.out.println("🔍 Raw password: " + rawPassword);
        System.out.println("🔐 Encoded password in DB: " + user.getPassword());

        boolean matches = passwordEncoder.matches(rawPassword, user.getPassword());
        System.out.println("🔄 Password match result: " + matches);

        return matches;
    }

}
