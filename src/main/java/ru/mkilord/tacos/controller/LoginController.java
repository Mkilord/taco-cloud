package ru.mkilord.tacos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@RequestMapping("/login")
@Controller
public class LoginController {
    @GetMapping
    public String loginProcess(
            @RequestParam(value = "error", required = false)
            Optional<String> error,
            @RequestParam(value = "username", required = false)
            Optional<String> username,
            Model model
    ) {
        username.ifPresent(present -> model.addAttribute("username", username.get()));
        error.ifPresent(present -> model.addAttribute("errorMsg", "Invalid username or password."));
        return "login";
    }
}
