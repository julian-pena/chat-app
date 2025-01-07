package com.chat.app.controller;

import com.chat.app.config.exceptions.UserNameDuplicatedException;
import com.chat.app.config.security.userdetails.UserDetailsServiceImpl;
import com.chat.app.model.dto.user.UserRegistrationDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@CrossOrigin
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Operations for login and user registration")
public class AuthController {

    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public AuthController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @GetMapping("/login")
    public String getLoginPage() {
        return "login-form"; // Serves the login form
    }

    // Post mapping for login is taken care by Spring Security


    @GetMapping("/register")
    public String getRegistrationForm(Model model) {
        model.addAttribute("userRegistrationDTO", new UserRegistrationDTO());
        return "registration-form";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("userRegistrationDTO") @Valid UserRegistrationDTO userRegistrationDTO,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "registration-form";
        }

        try {
            userDetailsService.registerUser(userRegistrationDTO);
            // Añadir un mensaje de éxito usando RedirectAttributes
            redirectAttributes.addFlashAttribute("successMessage", "User registered successfully! Please log in.");
            return "redirect:/auth/login";
        } catch (UserNameDuplicatedException e) {
            model.addAttribute("usernameError", e.getMessage());
            return "registration-form";
        }
    }


}


