package com.example.parking.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorViewController {

    @GetMapping("/403")
    public String error403() {
        return "403"; // refers to templates/403.html
    }
}