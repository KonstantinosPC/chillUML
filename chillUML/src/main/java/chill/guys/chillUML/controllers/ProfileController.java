package chill.guys.chillUML.controllers;

import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.services.ProfileServicesImpl;
import chill.guys.chillUML.services.UserServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProfileController {

    @Autowired
    UserServicesImpl userServices;

    @Autowired
    ProfileServicesImpl profileServices;

    @GetMapping("/profile")
    public String toProfile(@AuthenticationPrincipal UserDetails userDetails, Model model){
        User currentUser = userServices.findByUsername(userDetails.getUsername()).orElse(null);

        model.addAttribute("user",currentUser);
        return "profile";
    }

    @PostMapping("/profile/password")
    public String changePassword(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("old-password") String oldPassword, @RequestParam("new-password") String newPassword, @RequestParam("conf-password") String confPassword, RedirectAttributes redirectAttributes){
        profileServices.changePassword(userServices.findByUsername(userDetails.getUsername()).get(), oldPassword, newPassword, confPassword, redirectAttributes);
        return "redirect:/profile";
    }


    @PostMapping("/profile/username")
    public String changeUsername(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("username") String newUsername, RedirectAttributes redirectAttributes){
        profileServices.changeUsername(userServices.findByUsername(userDetails.getUsername()).get(), newUsername, redirectAttributes);
        return "redirect:/profile";
    }

    @PostMapping("/profile/email")
    public String changeEmail(@AuthenticationPrincipal UserDetails userDetails,@RequestParam("email") String newEmail, RedirectAttributes redirectAttributes){
        profileServices.changeEmail(userServices.findByUsername(userDetails.getUsername()).get(), newEmail, redirectAttributes);
        return "redirect:/profile";
    }
}
