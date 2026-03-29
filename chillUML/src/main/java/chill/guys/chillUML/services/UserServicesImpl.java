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

    @Autowired
    private UserRepository userDAO;

    @Override
    public Model saveUser(User user, String confirmationPassword) {
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
        if (findByEmail(user.getEmail()) != null) {
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
            userDAO.save(user);
        } else {
            // Kanto opws thes gia na bgainoun ta mhnymata ekei pou thes <3
            System.out.print("The password and the password confirmation do not match");
            // Kwstaki kanto na mhn proxwraei
        }
    }

    @Override
    public boolean isUserPresent(String username) {
        return userDAO.findByUsername(username) != null;
    }

    @Override
    public boolean login(String username, String password) {
        String hashed = passwordEncoder.encode(password);
        User user = findByUsername(username);
        if(user != null){
            if(user.verifyPassword(hashed)){
                return true;
            }else{
                // Kanto opws thes gia na bgainoun ta mhnymata ekei pou thes <3
                System.out.print("The password is incorect");
                // Kwstaki kanto na mhn proxwraei
            }
        }else{
            // Kanto opws thes gia na bgainoun ta mhnymata ekei pou thes <3
            System.out.print("This user does not exist");
            // Kwstaki kanto na mhn proxwraei
        }
        return user.verifyPassword(hashed);
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
