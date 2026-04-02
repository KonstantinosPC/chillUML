package chill.guys.chillUML.services;


import chill.guys.chillUML.domain.RegistrationForm;
import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.swing.text.html.Option;
import java.util.Optional;


@Service
public class UserServicesImpl implements UserServices, UserDetailsService {

    CharSequence specialChars = "+-*/%=!<>&|^~(){}[];,.?:@_$";

    @Autowired
    private final PasswordEncoder passwordEncoder = new MessageDigestPasswordEncoder("SHA-256");

    @Autowired
    private UserRepository userDAO;

    @Override
    @Transactional
    public void saveUser(RegistrationForm form, RedirectAttributes redirectAttributes) {
        if (!isUserPresent(form.getUsername())) {
            if (form.getUsername().length() > 15) {
                redirectAttributes.addAttribute("errors","This username is over 15 characters long");
                return ;
            }
        } else {
            redirectAttributes.addAttribute("errors","This username already exists");
            return ;
        }
        if (findByEmail(form.getEmail()).isEmpty()) {
            redirectAttributes.addAttribute("errors","This email is already in use");
            return ;
        }
        if (form.getPassword().equals(form.getConfirmPassword())) {
            if (form.getConfirmPassword().length() < 8) {
                redirectAttributes.addAttribute("errors","Password >= 8 characters long");
                return ;
            }
            if (!(form.getConfirmPassword().contains(specialChars))) {
                redirectAttributes.addAttribute("errors","Please use at least one special character");
                return ;
            }

            String hashed = passwordEncoder.encode(form.getPassword());
            User user = new User();
            user.setUsername(form.getUsername());
            user.setEmail(form.getEmail());
            user.setPassword(hashed);
            userDAO.save(user);
        } else {
            redirectAttributes.addAttribute("errors","The password and the confirmation password do not match");
        }
        return ;
    }

    @Override
    public boolean isUserPresent(String username) {
        return userDAO.findByUsername(username).isPresent();
    }

    @Override
    public void login(String username, String password) {
//        String hashed = passwordEncoder.encode(password);
//        User user = findByUsername(username).get();
//        if(user != null){
//            if(user.verifyPassword(hashed)){
//                return null;
//            }else{
//                model.addAttribute("errors","The password is incorrect");
//                return model;
//            }
//        }else{
//            model.addAttribute("errors","This user does not exist");
//            return model;
//        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return (UserDetails) userDAO.findByUsername(username).orElseThrow(
                ()-> new UsernameNotFoundException(
                        String.format("USER_NOT_FOUND", username)
                ));
    }

    @Override
    public Optional<User> findById(int id) {
        return userDAO.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    public Optional<User> findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

}
