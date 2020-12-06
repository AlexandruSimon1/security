package com.example.security.controllers;

import com.example.security.dto.UserDTO;
import com.example.security.entities.User;
import com.example.security.services.SendEmailService;
import com.example.security.services.UserService;
import freemarker.template.TemplateException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/forgotPassword/{username}")
    public String forgotPassword(@PathVariable String username) throws MessagingException, IOException, TemplateException {
        return userService.forgotPassword(username);
    }

    @PostMapping("/user/register")
    public User saveUser(@RequestBody User user) throws Exception {
        return userService.createUser(user);
    }

    @GetMapping("/activation/{activationCode}")
    public String activateUser(@PathVariable String activationCode) {
        return userService.activatedUser(UUID.fromString(activationCode));
    }

    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    @GetMapping("/admin/users")
    public List<User> findAll() {
        return userService.findAllUsers();
    }

    @GetMapping("/admin/user/{id}")
    public User findById(@PathVariable Long id, Authentication authentication) throws Exception {
        return userService.findByIdAdmin(id, authentication);
    }

    @DeleteMapping("/user/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        return userService.deleteById(id);
    }

    @GetMapping("/user/{id}")
    public UserDTO findByIdUser(@PathVariable Long id) {
        return userService.findByIdUser(id);
    }

}
