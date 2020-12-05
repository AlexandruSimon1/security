package com.example.security.controllers;

import com.example.security.dto.UserDTO;
import com.example.security.entities.User;
import com.example.security.model.MailModel;
import com.example.security.services.SendEmailService;
import com.example.security.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {
    private final UserService userService;
    private final SendEmailService sendEmailService;

    public UserController(UserService userService, SendEmailService sendEmailService) {
        this.userService = userService;
        this.sendEmailService = sendEmailService;
    }

    @PostMapping("/forgotPassword/{username}")
    public String forgotPassword(@RequestParam User user,@PathVariable String username){
        return userService.existingUser(user.getActivationCode());
    }

//    @PatchMapping("/user/password/{activationCode}")
//    public String updatePassword(@RequestBody User user,@PathVariable String activationCode){
//        return userService.
//    }

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
