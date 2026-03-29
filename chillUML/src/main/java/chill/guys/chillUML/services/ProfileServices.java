package chill.guys.chillUML.services;

public interface ProfileServices {
    void changeProfilePicture(int userId, String profilePicture);
    void changeProfileUsername(int userId, String username);
    void changeProfileEmail(int userId, String email);
    void changeProfilePassword(int userId, String currentPassword, String newPassword);
}