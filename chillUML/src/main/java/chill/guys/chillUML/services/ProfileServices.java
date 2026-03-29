package chill.guys.chillUML.services;

import chill.guys.chillUML.domain.User;

public interface ProfileServices {
    void changeProfilePicture(User user, String profilePicture);
    void changeProfileUsername(User user, String username);
    void changeProfileEmail(User user, String email);
    void changeProfilePassword(User user, String currentPassword, String newPassword);
}