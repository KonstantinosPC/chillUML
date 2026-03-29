package chill.guys.chillUML.services;
import chill.guys.chillUML.domain.User;

import java.util.Optional;

public interface UserService {
    public void saveUser(User user,String confirmationPassword);
    public boolean isUserPresent(String username);
    public boolean login(String username,String password);
    public Optional<User> findById(int id);
    public Optional<User> findByEmail(String email);
}