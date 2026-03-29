package chill.guys.chillUML.services;
import chill.guys.chillUML.domain.User;
import org.springframework.ui.Model;

public interface UserServices {
    public Model saveUser(User user,String confirmationPassword);
    public boolean isUserPresent(String username);
    public boolean login(String username,String password);
    public User findById(int id);
    public User findByEmail(String email);
}