package chill.guys.chillUML.services;


import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.repositories.UserRepository;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UserServicesImpl implements UserServices {

    CharSequence specialChars = "+-*/%=!<>&|^~(){}[];,.?:@_$";
    @Autowired
    private final PasswordEncoder passwordEncoder = new MessageDigestPasswordEncoder("SHA-256");
    Model model;
    @Autowired
    private UserRepository userDAO;

    @Override
    public Model saveUser(User user, String confirmationPassword) {
        if (!isUserPresent(user.getUsername())) {
            if (user.getUsername().length() > 15) {
                model.addAttribute("errors","This username is over 15 characters long");
                return model;
            }
        } else {
            model.addAttribute("errors","This username already exists");
            return model;
        }
        if (findByEmail(user.getEmail()) != null) {
            model.addAttribute("errors","This email is already in use");
            return model;
        }
        if (user.verifyPassword(confirmationPassword)) {
            if (confirmationPassword.length() < 8) {
                model.addAttribute("errors","Password >= 8 characters long");
                return model;
            }
            if (!(confirmationPassword.contains(specialChars))) {
                model.addAttribute("errors","Please use at least one special character");
                return model;
            }

            String hashed = passwordEncoder.encode(confirmationPassword);
            user.setPassword(hashed);
            userDAO.save(user);
            return null;
        } else {
            model.addAttribute("errors","The password and the password confirmation do not match");
            return model;
        }
    }

    @Override
    public boolean isUserPresent(String username) {
        return userDAO.findByUsername(username) != null;
    }

    @Override
    public Model login(String username, String password) {
        String hashed = passwordEncoder.encode(password);
        User user = findByUsername(username);
        if(user != null){
            if(user.verifyPassword(hashed)){
                return null;
            }else{
                model.addAttribute("errors","The password is incorrect");
                return model;
            }
        }else{
            model.addAttribute("errors","This user does not exist");
            return model;
        }
    }

    @Override
    public User findById(int id) {
        return userDAO.findById(id);
    }

    @Override
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

}
