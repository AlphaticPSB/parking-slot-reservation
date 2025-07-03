package com.example.parking.view;

import com.example.parking.model.User;
import com.example.parking.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Redirect root to home page
    @GetMapping("/")
    public String indexRedirect() {
        return "redirect:/home";
    }

    // Home page
    @GetMapping("/home")
    public String homePage() {
        return "home";
    }

    // Login page
    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "registered", required = false) String registered,
                            @RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (registered != null) {
            model.addAttribute("message", "✅ Registration successful! Please log in.");
        }
        if (error != null) {
            model.addAttribute("error", "❌ Invalid username or password");
        }
        if (logout != null) {
            model.addAttribute("message", "✅ Logged out successfully.");
        }
        return "login";
    }

    // Register page
    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Handle registration
    @PostMapping("/register")
    public String processRegister(@ModelAttribute("user") @Valid User user,
                                  BindingResult result,
                                  Model model) {

        if (result.hasErrors()) return "register";

        if (userRepository.existsByUsername(user.getUsername())) {
            model.addAttribute("message", "❌ Username already exists");
            return "register";
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("USER"); // Default role
        user.setEnabled(true);
        userRepository.save(user);

        return "redirect:/login?registered=true";
    }
}