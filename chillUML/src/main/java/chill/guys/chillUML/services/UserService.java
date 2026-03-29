package chill.guys.chillUML.services;
import chill.guys.chillUML.domain.User;

public interface UserService {
    public void saveUser(User user,String confirmationPassword);
    public boolean isUserPresent(User user);
    public boolean login(String username,String password);
    public User findById(int id);
    public User findByEmail(String email);
}