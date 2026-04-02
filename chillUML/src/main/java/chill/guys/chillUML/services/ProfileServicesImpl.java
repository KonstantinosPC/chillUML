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
    public void changeProfilePicture(User user, String profilePicture) {
        //User user = userDAO.findById(userId);
        if (user != null) {
            user.setProfilePicture(profilePicture);
            userDAO.save(user);
        }
    }

    @Override
    public void changeProfileUsername(User user, String username) {
        //User user = userDAO.findById(userId);
        if (user != null) {
            user.setUsername(username);
            userDAO.save(user);
        }
    }

    @Override
    public void changeProfileEmail(User user, String email) {
        //User user = userDAO.findById(userId);
        if (user != null) {
            user.setEmail(email);
            userDAO.save(user);
        }
    }

    @Override
    public void changeProfilePassword(User  user, String currentPassword, String newPassword) {
        //User user = userDAO.findById(userId);
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