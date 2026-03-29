package chill.guys.chillUML.services;

import chill.guys.chillUML.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServicesImpl implements ProfileServices{
    CharSequence specialChars = "+-*/%=!<>&|^~(){}[];,.?:@_$";
    @Autowired
    private final PasswordEncoder passwordEncoder = new MessageDigestPasswordEncoder("SHA-256");

    @Autowired
    private UserRepository userDAO;


    @Override
    public void changeProfilePicture(String profilePicture) {

    }

    @Override
    public void changeProfileUsername(String profilePicture) {

    }

    @Override
    public void changeProfilePassword(String profilePicture) {

    }

    @Override
    public void changeProfileEmail(String profilePicture) {

    }

    private verifyPassword(String password){

    }
}
