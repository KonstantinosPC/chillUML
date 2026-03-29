package chill.guys.chillUML.services;

public interface ProfileServices {
    void changeProfilePicture(String profilePicture);
    void changeProfileUsername(String username);
    void changeProfileEmail(String email);
    void changeProfilePassword(String currentPassword, String newPassword);
}