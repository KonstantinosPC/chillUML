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
    public void changeProfilePicture(int userId, String profilePicture) {
        User user = userDAO.findById(userId);
        if (user != null) {
            user.setProfilePicture(profilePicture);
            userDAO.save(user);
        }
    }

    @Override
    public void changeProfileUsername(int userId, String username) {
        User user = userDAO.findById(userId);
        if (user != null) {
            user.setUsername(username);
            userDAO.save(user);
        }
    }

    @Override
    public void changeProfileEmail(int userId, String email) {
        User user = userDAO.findById(userId);
        if (user != null) {
            user.setEmail(email);
            userDAO.save(user);
        }
    }

    @Override
    public void changeProfilePassword(int userId, String currentPassword, String newPassword) {
        User user = userDAO.findById(userId);

        if (user != null) {
            // 1. Κρυπτογραφούμε τον τρέχοντα κωδικό που έδωσε ο χρήστης
            String hashedCurrent = passwordEncoder.encode(currentPassword);

            // 2. Ελέγχουμε αν ταιριάζει με αυτόν που έχει η βάση (χρήση της verifyPassword του User)
            if (user.verifyPassword(hashedCurrent)) {

                // 3. Αν είναι σωστός, κρυπτογραφούμε τον ΝΕΟ κωδικό
                String hashedNew = passwordEncoder.encode(newPassword);

                // 4. Ενημέρωση και αποθήκευση
                user.setPassword(hashedNew);
                userDAO.save(user);
                System.out.println("Password updated successfully.");
            } else {
                System.out.println("Current password is incorrect.");
            }
        }
    }
}