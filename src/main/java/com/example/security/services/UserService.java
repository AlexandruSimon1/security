package com.example.security.services;

import com.example.security.dto.UserDTO;
import com.example.security.entities.User;
import freemarker.template.TemplateException;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface UserService {
    User createUser(User user) throws MessagingException, IOException, TemplateException;
    User updateUser(Long id, User user);
    List<User> findAllUsers();
    User findByIdAdmin(Long id, Authentication authentication) throws Exception;
    String deleteById(Long id);
    UserDTO findByIdUser(Long id);
    String activatedUser(UUID activationCode);
    String forgotPassword(String username) throws MessagingException, IOException, TemplateException;
    String checkUserByForgotPassword(UUID forgotPassword, Model model);
    User newPassword(User user);
}
