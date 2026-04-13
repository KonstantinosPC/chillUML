package chill.guys.chillUML.services;

import chill.guys.chillUML.domain.User;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public interface ProfileServices {
    public void changeProfilePicture(User user, String newProfilePicture);
    public void changeUsername(User user, String newUsername, RedirectAttributes redirectAttributes);
    public void changePassword(User user,String confirmationPassword,String newPassword,String newPasswordConfirmation,RedirectAttributes redirectAttributes);
    public void changeEmail(User user,String newEmail, RedirectAttributes redirectAttributes);
    //public void deleteAccount();
}
