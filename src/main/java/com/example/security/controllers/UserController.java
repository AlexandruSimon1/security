package com.example.security.controllers;

import com.example.security.dto.UserDTO;
import com.example.security.entities.User;
import com.example.security.model.MailModel;
import com.example.security.services.SendEmailService;
import com.example.security.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private final UserService userService;
    private final SendEmailService sendEmailService;

    public UserController(UserService userService, SendEmailService sendEmailService) {
        this.userService = userService;
        this.sendEmailService = sendEmailService;
    }

    @PostMapping("/user")
    public User saveUser(@RequestBody User user) throws Exception {
        return userService.createUser(user);
    }

    @GetMapping("/user/activation/{username}")
    public String activateUser(@RequestBody User user) {
        user.setEnabled(true);
        return "User was activated";
    }

    @PutMapping("/user/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user){
        return userService.updateUser(id, user);
    }

    @GetMapping("/admin/users")
    public List<User> findAll(){
        return userService.findAllUsers();
    }

    @GetMapping("/admin/user/{id}")
    public User findById(@PathVariable Long id, Authentication authentication) throws Exception {
        return userService.findByIdAdmin(id, authentication);
    }

    @DeleteMapping("/user/{id}")
    public String deleteById(@PathVariable("id") Long id){
       return userService.deleteById(id);
    }

    @GetMapping("/user/{id}")
    public UserDTO findByIdUser(@PathVariable Long id){
        return userService.findByIdUser(id);
    }

}
