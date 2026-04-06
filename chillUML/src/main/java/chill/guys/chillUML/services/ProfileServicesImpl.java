package chill.guys.chillUML.services;

import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.repositories.UserRepository;
<<<<<<< Updated upstream
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
=======
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



public class ProfileServicesImpl implements ProfileServices, UserDetailsService {

    String specialChars = "+-*/%=!<>&|^~(){}[];,.?:@_$";


    private final PasswordEncoder passwordEncoder = new MessageDigestPasswordEncoder("SHA-256");

    @Autowired
    private UserRepository userRepository;

    @Override
    public void changeProfilePicture(int userID, String newProfilePicture) {
        User user = findById(userID).get();
        user.setProfilePicture(newProfilePicture);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void changeUsername(int userID,String newUsername,RedirectAttributes redirectAttributes) {

        if(userRepository.findByUsername(newUsername).isEmpty()){
            if(newUsername.length()<= 15){
                User user = findById(userID).get();
                user.setUsername(newUsername);
                userRepository.save(user);
            }else{
                redirectAttributes.addFlashAttribute("error","This username is over 15 characters long");
            }

        }else{
            redirectAttributes.addFlashAttribute("error","This username already exists");
        }


    }

    @Override
    public void changePassword(int userID,String confirmationPassword,String newPassword,String newPasswordConfirmation,RedirectAttributes redirectAttributes) {

        String hashed = passwordEncoder.encode(confirmationPassword);
        String newHashed = passwordEncoder.encode(newPassword);
        User user = findById(userID).get();
        if(!user.verifyPassword(hashed)){
            redirectAttributes.addFlashAttribute("error","Old Password is Wrong");
            return;
        }
        if(confirmationPassword.chars().noneMatch(ch->specialChars.indexOf(ch) >= 0)) {
            redirectAttributes.addFlashAttribute("error","Please use at least one special character");
            return;
        }
        if(newPassword.length() < 8){
            redirectAttributes.addFlashAttribute("error","Password >= 8 characters long");
            return ;
        }
        if(newHashed.equals(hashed)){
            redirectAttributes.addFlashAttribute("error","New password is the same as the old one");
            return;
        }
        if(!(newPassword.equals(newPasswordConfirmation))){
            redirectAttributes.addFlashAttribute("error","Confirmation password not the same as the new password");
        }
        user.setPassword(newHashed);
    }

    @Override
    public void changeEmail(int userID,String newEmail,RedirectAttributes redirectAttributes) {
        if(userRepository.findByEmail(newEmail).isEmpty()){
            User user = userRepository.findById(userID).get();
            user.setEmail(newEmail);
        }else{
            redirectAttributes.addFlashAttribute("error","This email is already in use");
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

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }
}
>>>>>>> Stashed changes
