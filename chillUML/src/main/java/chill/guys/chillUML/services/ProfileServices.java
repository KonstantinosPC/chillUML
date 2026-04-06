package chill.guys.chillUML.services;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface ProfileServices {
    public void changeProfilePicture(int userID,String newProfilePicture);
    public void changeUsername(int userID, String newUsername, RedirectAttributes redirectAttributes);
    public void changePassword(int userID,String confirmationPassword,String newPassword,String newPasswordConfirmation,RedirectAttributes redirectAttributes);
    public void changeEmail(int userID,String newEmail, RedirectAttributes redirectAttributes);
    //public void deleteAccount();
}
