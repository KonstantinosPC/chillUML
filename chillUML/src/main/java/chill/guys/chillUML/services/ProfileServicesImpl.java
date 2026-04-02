package chill.guys.chillUML.services;

import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServicesImpl implements ProfileServices {

    @Autowired
    private UserRepository userDAO;

    private final PasswordEncoder passwordEncoder = new MessageDigestPasswordEncoder("SHA-256");

    @Override
    public void changeProfilePicture(int userID, String profilePicture) {
        User user = userDAO.findById(userID);
        if (user != null) {
            user.setProfilePicture(profilePicture);
            userDAO.save(user);
        }
    }

    @Override
    public void changeProfileUsername(int userID, String username) {

        User user = userDAO.findById(userID);
        if (user != null) {
            if(username.length() > 15){
                // "This username is over 15 characters long"
                return;
            }
            if(userDAO.findByUsername(username) != null){
                //"This username already exists"
                return;
            }
            user.setUsername(username);
            userDAO.save(user);
        }
    }

    @Override
    public void changeProfileEmail(int userID, String email) {
        User user = userDAO.findById(userID);
        if (user != null) {
            if(userDAO.findByEmail(email).isEmpty()){
                //
            }
            user.setEmail(email);
            userDAO.save(user);
        }
    }

    @Override
    public void changeProfilePassword(int userID, String currentPassword, String newPassword) {
        User user = userDAO.findById(userID);
        if (user != null) {
            String hashedCurrent = passwordEncoder.encode(currentPassword);
            if (user.verifyPassword(hashedCurrent)) {
                String hashedNew = passwordEncoder.encode(newPassword);
                user.setPassword(hashedNew);
                userDAO.save(user);
                System.out.println("Password updated successfully.");
            } else {
                System.out.println("Current password is incorrect.");
            }
        }
    }
}