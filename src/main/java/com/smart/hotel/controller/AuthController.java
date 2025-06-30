// ✅ AuthController.java - Ensures 'name' is captured and saved properly
package com.smart.hotel.controller;

import com.smart.hotel.model.User;
import com.smart.hotel.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, Model model) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email already registered!");
            return "register";
        }

        // Debug print to verify name
        System.out.println("Registered user name: " + user.getName());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user); // ✅ user.getName() must not be null

        return "redirect:/login?registered";
    }
}