package com.example.security.controllers;

import com.example.security.entities.User;
import com.example.security.repositories.UserRepository;
import com.example.security.services.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
public class ResetPasswordController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public ResetPasswordController(UserService userService, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

/*    @GetMapping("/password/{forgotPassword}")
    public String updatePassword(@PathVariable String forgotPassword, Model model) {
        return userService.checkUserByForgotPassword(UUID.fromString(forgotPassword), model);
    }*/

    @PostMapping("/resetPassword")
    public String resetPassword(User user, Model model) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "success.ftl.html";
    }

    @RequestMapping("/password/{forgotPassword}")
    public ModelAndView updatePassword(@PathVariable String forgotPassword, Model model) {
        return new ModelAndView("resetPassword.ftl.html", "user", userRepository.findByForgotPassword(UUID.fromString(forgotPassword)));
    }
}
