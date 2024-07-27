package ru.mkilord.tacos.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.mkilord.tacos.data.UserRepository;
import ru.mkilord.tacos.security.RegistrationForm;

@Controller
@RequestMapping("/register")
@AllArgsConstructor
public class RegisterController {
    private UserRepository userRepo;
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String registerForm() {
        return "registration";
    }

    @PostMapping
    public String processRegistration(Errors errors, RegistrationForm form) {
        if (errors.hasErrors())return "/register";
        userRepo.save(form.toUser(passwordEncoder));
        return "redirect:/login";
    }
}
