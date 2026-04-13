package chill.guys.chillUML.services;


import java.util.Optional;

import chill.guys.chillUML.DTO.RegistrationForm;
import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class UserServicesImpl implements UserServices, UserDetailsService {

    String specialChars = "+-*/%=!<>&|^~(){}[];,.?:@_$";


    private final PasswordEncoder passwordEncoder = new MessageDigestPasswordEncoder("SHA-256");

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public void saveUser(RegistrationForm form, RedirectAttributes redirectAttributes) {
        if (!isUserPresent(form.getUsername())) {
            if (form.getUsername().length() > 15) {
                redirectAttributes.addFlashAttribute("error","This username is over 15 characters long");
                return ;
            }
        } else {
            redirectAttributes.addFlashAttribute("error","This username already exists");
            return ;
        }
        if (!(findByEmail(form.getEmail()).isEmpty())) {
            redirectAttributes.addFlashAttribute("error","This email is already in use");
            return ;
        }
        if (form.getPassword().equals(form.getConfirmPassword())) {
            if (form.getPassword().length() < 8) {
                redirectAttributes.addFlashAttribute("error","Password >= 8 characters long");
                return ;
            }
            if (form.getPassword().chars().noneMatch(ch->specialChars.indexOf(ch) >= 0)) {
                redirectAttributes.addFlashAttribute("error","Please use at least one special character");
                return ;
            }

            String hashed = passwordEncoder.encode(form.getPassword());
            User user = new User();
            user.setUsername(form.getUsername());
            user.setEmail(form.getEmail());
            user.setPassword(hashed);
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("success","The user " + user.getUsername() + " has been created successfully");
        } else {
            redirectAttributes.addFlashAttribute("error","The password and the confirmation password do not match");
        }
        return ;
    }

    @Override
    public boolean isUserPresent(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public boolean login(String username, String password) {
        String hashed = passwordEncoder.encode(password);
        Optional<User> user = findByUsername(username);
        if(user.isEmpty() || hashed != null){
            return false;
        }else{
            return user.get().verifyPassword(hashed);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)

                .map(myUser -> org.springframework.security.core.userdetails.User.builder()
                        .username(myUser.getUsername())
                        .password(myUser.getPassword())
                        .roles("USER")
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


}