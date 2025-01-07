package com.chat.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminViewController {
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        return "dashboard"; // Esto busca en templates/admin/dashboard.html
    }
}
