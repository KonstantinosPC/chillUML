package chill.guys.chillUML.services;

import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServicesImpl implements ProfileServices {

    @Autowired
    private UserRepository userDAO;

    private final PasswordEncoder passwordEncoder = new MessageDigestPasswordEncoder("SHA-256");
    
    private User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userDAO.findByUsername(username);
    }

    @Override
    public void changeProfilePicture(String profilePicture) {
        User user = getAuthenticatedUser();
        if (user != null) {
            user.setProfilePicture(profilePicture);
            userDAO.save(user);
        }
    }

    @Override
    public void changeProfileUsername(String username) {
        User user = getAuthenticatedUser();
        if (user != null) {
            user.setUsername(username);
            userDAO.save(user);
        }
    }

    @Override
    public void changeProfileEmail(String email) {
        User user = getAuthenticatedUser();
        if (user != null) {
            user.setEmail(email);
            userDAO.save(user);
        }
    }

    @Override
    public void changeProfilePassword(String currentPassword, String newPassword) {
        User user = getAuthenticatedUser();
        if (user != null) {
            String hashedCurrent = passwordEncoder.encode(currentPassword);
            if (user.verifyPassword(hashedCurrent)) {
                String hashedNew = passwordEncoder.encode(newPassword);
                user.setPassword(hashedNew);
                userDAO.save(user);
            }
        }
    }
}