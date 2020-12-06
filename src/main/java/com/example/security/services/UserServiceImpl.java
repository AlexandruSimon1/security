package com.example.security.services;

import com.example.security.dto.UserDTO;
import com.example.security.entities.User;
import com.example.security.model.MailModel;
import com.example.security.repositories.UserPersonalInfoRepository;
import com.example.security.repositories.UserRepository;
import com.example.security.utils.DTOMapper;
import com.example.security.utils.EmailTemplate;
import freemarker.template.TemplateException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserPersonalInfoRepository userPersonalInfoRepository;
    private final SendEmailService sendEmailService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           UserPersonalInfoRepository userPersonalInfoRepository, SendEmailService sendEmailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userPersonalInfoRepository = userPersonalInfoRepository;
        this.sendEmailService = sendEmailService;
    }

    private MailModel createMailModelForCreatedUser(User user) {
        MailModel mailModel = new MailModel();
        mailModel.setTo(user.getUserPersonalInfo().getEmail());
        mailModel.setTemplate(EmailTemplate.REGISTER);
        mailModel.setSubject("Activation Email");
        HashMap<String, String> content = new HashMap<>();
        content.put("username", user.getUsername());
        content.put("activationCode", user.getActivationCode().toString());
        content.put("firstName", user.getUserPersonalInfo().getFirstName());
        content.put("lastName", user.getUserPersonalInfo().getLastName());
        mailModel.setContent(content);
        return mailModel;
    }

    private MailModel createMailModelForForgotPassword(User user) {
        MailModel mailModel = new MailModel();
        mailModel.setTo(user.getUserPersonalInfo().getEmail());
        mailModel.setTemplate(EmailTemplate.FORGOTPASSWORD);
        mailModel.setSubject("Forgot Password");
        HashMap<String, String> content = new HashMap<>();
        content.put("forgotPassword", user.getForgotPassword().toString());
        content.put("username", user.getUsername());
        mailModel.setContent(content);
        return mailModel;
    }

    @Override
    public String forgotPassword(String username) throws MessagingException, IOException, TemplateException {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            user.get().setForgotPassword(UUID.randomUUID());
            userRepository.save(user.get());
            MailModel mailModel = createMailModelForForgotPassword(user.get());
            sendEmailService.send(mailModel);
            return "Created forgot password UUID for verification email has been sent";
        }
        return "User doesn't exist";
    }

    public String checkUserByForgotPassword(UUID forgotPassword, Model model) {
        User user = userRepository.findByForgotPassword(forgotPassword);
        if (user != null) {
            model.addAttribute("user", user);
            return "resetPassword.ftl.html";
        }
        return "User doesn't exist";
    }



    public User newPassword(User user) {
        String password = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        userRepository.save(user);
        return user;
    }

    public String activatedUser(UUID activationCode) {
        User user = userRepository.findByActivationCode(activationCode);
        if (user != null) {
            if (user.getEnabled()) {
                return "This username has already been activated!";
            } else {
                return "Hello " + user.getUsername() + ", your account has been activated";
            }
        }
        return "Invalid activationCode";
    }

    @Override
    public User createUser(User user) throws MessagingException, IOException, TemplateException {
        String password = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(password);
        user.setActivationCode(UUID.randomUUID());
        User userSaved = userRepository.save(user);
        MailModel mailModel = createMailModelForCreatedUser(user);
        sendEmailService.send(mailModel);
        return userSaved;
    }

    @Override
    @Transactional
    public User updateUser(Long id, User user) {
        User optionalUser = userRepository.findUserById(id);
        optionalUser.setPassword(passwordEncoder.encode(user.getPassword()));
        optionalUser.setEnabled(user.getEnabled());
        optionalUser.setUserAuthorities(user.getUserAuthorities());
        optionalUser.setUserPersonalInfo(user.getUserPersonalInfo());

        userRepository.save(optionalUser);
        return optionalUser;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    public boolean isAdmin(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        for (GrantedAuthority userAuthority : userDetails.getAuthorities()) {
            if (userAuthority.toString().equals("ADMIN")) {
                return true;
            }
        }

        return false;
    }

    @Override
    public User findByIdAdmin(Long id, Authentication authentication) throws Exception {
        boolean isAdmin = this.isAdmin(authentication);
        if (isAdmin) {
            return userRepository.findUserById(id);
        } else {
            throw new Exception("User is not admin!");
        }
    }


    @Override
    public String deleteById(Long id) {
        userRepository.deleteById(id);
        return "User was deleted";
    }

    @Override
    public UserDTO findByIdUser(Long id) {
        User user = userRepository.findUserById(id);
        return DTOMapper.mapUserToDTO(user);
    }

}
