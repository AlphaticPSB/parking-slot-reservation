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
        // Check if admin user exists
        if (!userRepository.existsByUsername("PSB")) {
            User admin = new User();
            admin.setUsername("PSB");
            admin.setEmail("PSB@gmail.com");

            // Encode password "123"
            String encodedPassword = passwordEncoder.encode("123");
            admin.setPassword(encodedPassword);

            // Set role as ADMIN
            admin.setRole("ADMIN");

            admin.setEnabled(true); // ensure account is active

            userRepository.save(admin);
            System.out.println("👉 Admin user created: username=PSB, password=123");
        } else {
            System.out.println("✅ Admin user already exists");
        }
    }
}
