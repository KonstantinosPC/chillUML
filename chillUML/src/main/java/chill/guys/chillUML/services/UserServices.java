package chill.guys.chillUML.services;
import chill.guys.chillUML.DTO.RegistrationForm;
import chill.guys.chillUML.domain.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

public interface UserServices {
    public void saveUser(RegistrationForm form, RedirectAttributes redirectAttributes);
    public boolean isUserPresent(String username);
    public boolean login(String username,String password);
    public Optional<User> findById(int id);
    public Optional<User> findByEmail(String email);
    public Optional<User> findByUsername(String username);
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}