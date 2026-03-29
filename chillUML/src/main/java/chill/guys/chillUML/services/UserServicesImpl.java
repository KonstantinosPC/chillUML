package chill.guys.chillUML.services;


import java.util.ArrayList;
import java.util.Arrays;
import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.repositories.UserRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


public class UserServicesImpl implements UserService {

    CharSequence specialChars = "+-*/%=!<>&|^~(){}[];,.?:@_$";
    @Autowired
    private final PasswordEncoder passwordEncoder = new MessageDigestPasswordEncoder("SHA-256");

    @Autowired
    private UserRepository userDAO;
    @Override
    public void saveUser(User user, String confirmaionPassword) {
        if (findByUsername(user.getUsername()) == null) {
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
        if (findByEmail(user.getEmail()) != null) {
            // Kanto opws thes gia na bgainoun ta mhnymata ekei pou thes <3
            System.out.print("This email is already in use");
            // Kwstaki kanto na mhn proxwraei
        }
        if (user.verifyPassword(confirmaionPassword)) {
            if (confirmaionPassword.length() < 8) {
                // Kanto opws thes gia na bgainoun ta mhnymata ekei pou thes <3
                System.out.print("Password >= 8 characters long");
                // Kwstaki kanto na mhn proxwraei
            }
            if (!(confirmaionPassword.contains(specialChars))) {
                // Kanto opws thes gia na bgainoun ta mhnymata ekei pou thes <3
                System.out.print("Please use at least one special character");
                // Kwstaki kanto na mhn proxwraei
            }

            String hashed = passwordEncoder.encode(confirmaionPassword);
            user.setPassword(hashed);
        } else {
            // Kanto opws thes gia na bgainoun ta mhnymata ekei pou thes <3
            System.out.print("The password and the password confirmation do not match");
            // Kwstaki kanto na mhn proxwraei
        }
    }

    @Override
    public boolean isUserPresent(User user) {
        return false;
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public User findById(int id) {
        return
                userDAO.findById(id);
    }

    @Override
    public User findByEmail(String email) {
        return null;
    }

    public User findByUsername(String username) {
        return null;
    }


}
