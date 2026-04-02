package chill.guys.chillUML.services;

import chill.guys.chillUML.domain.User;

public interface ProfileServices {
    void changeProfilePicture(int userID, String profilePicture);
    void changeProfileUsername(int userID, String username);
    void changeProfileEmail(int userID, String email);
    void changeProfilePassword(int userID, String currentPassword, String newPassword);
}