package chill.guys.chillUML.services;


import java.io.Serial;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.repositories.UserRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UserServicesImpl implements UserService, UserDetailsService {

    CharSequence specialChars = "+-*/%=!<>&|^~(){}[];,.?:@_$";


    private final PasswordEncoder passwordEncoder = new MessageDigestPasswordEncoder("SHA-256");

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveUser(User user, String confirmationPassword) {
        if (!isUserPresent(user.getUsername())) {
            if (user.getUsername().length() > 15) {
                // Kanto opws thes gia na bgainoun ta mhnymata ekei pou thes <3
                System.out.print("This username is over 15 characters long");
                // Kwstaki kanto na mhn proxwraei
            }
        } else {
            // Kanto opws thes gia na bgainoun ta mhnymata ekei pou thes <3
            System.out.print("This username already exists");
            // Kwstaki kanto na mhn proxwraei
        }
        if (!(findByEmail(user.getEmail()).isEmpty())) {
            // Kanto opws thes gia na bgainoun ta mhnymata ekei pou thes <3
            System.out.print("This email is already in use");
            // Kwstaki kanto na mhn proxwraei
        }
        if (user.verifyPassword(confirmationPassword)) {
            if (confirmationPassword.length() < 8) {
                // Kanto opws thes gia na bgainoun ta mhnymata ekei pou thes <3
                System.out.print("Password >= 8 characters long");
                // Kwstaki kanto na mhn proxwraei
            }
            if (!(confirmationPassword.contains(specialChars))) {
                // Kanto opws thes gia na bgainoun ta mhnymata ekei pou thes <3
                System.out.print("Please use at least one special character");
                // Kwstaki kanto na mhn proxwraei
            }

            String hashed = passwordEncoder.encode(confirmationPassword);
            user.setPassword(hashed);
            userRepository.save(user);
        } else {
            // Kanto opws thes gia na bgainoun ta mhnymata ekei pou thes <3
            System.out.print("The password and the password confirmation do not match");
            // Kwstaki kanto na mhn proxwraei
        }
    }
    
    @Override
    public boolean isUserPresent(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    @Override
    public boolean login(String username, String password) {
        String hashed = passwordEncoder.encode(password);
        Optional<User> user = findByUsername(username);
        return user.get().verifyPassword(hashed);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userRepository.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException(
                        String.format("USER_NOT_FOUND", username)
                ));
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
