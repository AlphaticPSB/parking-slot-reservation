package com.example.parking.config;

import com.example.parking.model.User;
import com.example.parking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Check if test user exists
        if (!userRepository.existsByUsername("testuser")) {
            User user = new User();
            user.setUsername("testuser");
            user.setEmail("test@example.com");

            // Encode password before saving
            String rawPassword = "test123";
            String encodedPassword = passwordEncoder.encode(rawPassword);
            user.setPassword(encodedPassword);

            // ✅ Save role as "USER" (not "ROLE_USER")
            user.setRole("USER");

            user.setEnabled(true); // Optional: ensure user is active

            userRepository.save(user);
            System.out.println("👉 Test user created: username=testuser, password=test123");
        } else {
            System.out.println("✅ Test user already exists");
        }
    }
}
