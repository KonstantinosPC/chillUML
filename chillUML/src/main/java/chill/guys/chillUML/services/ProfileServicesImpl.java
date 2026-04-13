package chill.guys.chillUML.services;

import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
public class ProfileServicesImpl implements ProfileServices, UserDetailsService {

    String specialChars = "+-*/%=!<>&|^~(){}[];,.?:@_$";

    private final PasswordEncoder passwordEncoder = new MessageDigestPasswordEncoder("SHA-256");

    @Autowired
    private UserRepository userRepository;

    @Override
    public void changeProfilePicture(User user, String newProfilePicture) {
        user.setProfilePicture(newProfilePicture);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void changeUsername(User user,String newUsername,RedirectAttributes redirectAttributes) {

        if(userRepository.findByUsername(newUsername).isEmpty()){
            if(newUsername.length()<= 15){
                user.setUsername(newUsername);
                userRepository.save(user);
            }else{
                redirectAttributes.addFlashAttribute("error","This username is over 15 characters long");
            }

        }else{
            redirectAttributes.addFlashAttribute("error","This username already exists");
        }
        redirectAttributes.addFlashAttribute("success","Your username has changed successfully");

    }


    @Override
    public void changePassword(User user,String confirmationPassword,String newPassword,String newPasswordConfirmation,RedirectAttributes redirectAttributes) {

        String newHashed = passwordEncoder.encode(newPassword);
        if(!passwordEncoder.matches(confirmationPassword, user.getPassword())){
            redirectAttributes.addFlashAttribute("error","Old Password is Wrong");
            return;
        }
        if(newPassword.chars().noneMatch(ch->specialChars.indexOf(ch) >= 0)) {
            redirectAttributes.addFlashAttribute("error","Please use at least one special character");
            return;
        }
        if(newPassword.length() < 8){
            redirectAttributes.addFlashAttribute("error","Password >= 8 characters long");
            return ;
        }
        if(confirmationPassword.equals(newPassword)){
            redirectAttributes.addFlashAttribute("error","New password is the same as the old one");
            return;
        }
        if(!(newPassword.equals(newPasswordConfirmation))){
            redirectAttributes.addFlashAttribute("error","Confirmation password not the same as the new password");
            return;
        }
        user.setPassword(newHashed);
        redirectAttributes.addFlashAttribute("success","Password changed successfully");
        userRepository.save(user);
    }

    @Override
    public void changeEmail(User user,String newEmail,RedirectAttributes redirectAttributes) {
        if(userRepository.findByEmail(newEmail).isEmpty()){
            user.setEmail(newEmail);
            userRepository.save(user);
            redirectAttributes.addFlashAttribute("success","Your email has changed successfully");
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

