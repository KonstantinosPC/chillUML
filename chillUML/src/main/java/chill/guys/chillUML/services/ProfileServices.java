package chill.guys.chillUML.services;

<<<<<<< Updated upstream
import chill.guys.chillUML.domain.User;

public interface ProfileServices {
    void changeProfilePicture(int userID, String profilePicture);
    void changeProfileUsername(int userID, String username);
    void changeProfileEmail(int userID, String email);
    void changeProfilePassword(int userID, String currentPassword, String newPassword);
}
=======
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface ProfileServices {
    public void changeProfilePicture(int userID,String newProfilePicture);
    public void changeUsername(int userID, String newUsername, RedirectAttributes redirectAttributes);
    public void changePassword(int userID,String confirmationPassword,String newPassword,String newPasswordConfirmation,RedirectAttributes redirectAttributes);
    public void changeEmail(int userID,String newEmail, RedirectAttributes redirectAttributes);
    //public void deleteAccount();
}
>>>>>>> Stashed changes
