package chill.guys.chillUML.controllers;

import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.services.ProfileServicesImpl;
import chill.guys.chillUML.services.UserServicesImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
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
    public String changePassword(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("old-password") String oldPassword, @RequestParam("new-password") String newPassword, @RequestParam("conf-password") String confPassword,Model model, RedirectAttributes redirectAttributes){
        User currentUser = userServices.findByUsername(userDetails.getUsername()).get();
        profileServices.changePassword(currentUser, oldPassword, newPassword, confPassword, redirectAttributes);
        model.addAttribute("user",currentUser);
        return "redirect:/profile";
    }

    @PostMapping("/profile/username")
    public String changeUsername(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("username") String newUsername, RedirectAttributes redirectAttributes, HttpServletRequest request){
        User currentUser = userServices.findByUsername(userDetails.getUsername()).get();
        profileServices.changeUsername(currentUser, newUsername, redirectAttributes);

        UserDetails newUser = userServices.loadUserByUsername(newUsername);

        Authentication newAuth = new UsernamePasswordAuthenticationToken(newUser, newUser.getPassword(), newUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(newAuth);

        SecurityContextRepository securityContextRepository = (SecurityContextRepository) request.getAttribute(HttpSessionSecurityContextRepository.class.getName());
        if (securityContextRepository != null) {
            securityContextRepository.saveContext(SecurityContextHolder.getContext(), (HttpServletRequest) request, (HttpServletResponse) request.getAttribute("jakarta.servlet.response"));
        }

        return "redirect:/profile";
    }

    @PostMapping("/profile/email")
    public String changeEmail(@AuthenticationPrincipal UserDetails userDetails,@RequestParam("email") String newEmail, Model model, RedirectAttributes redirectAttributes){
        User currentUser = userServices.findByUsername(userDetails.getUsername()).get();
        profileServices.changeEmail(currentUser, newEmail, redirectAttributes);
        model.addAttribute("user",currentUser);
        return "redirect:/profile";
    }

    @PostMapping("/profile/change-avatar")
    public String changeAvatar(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("avatar") String newAvatar, RedirectAttributes redirectAttributes){
        User currentUser = userServices.findByUsername(userDetails.getUsername()).get();
        profileServices.changeProfilePicture(currentUser, newAvatar);
        redirectAttributes.addFlashAttribute("success","Avatar has changed successfully");
        return "redirect:/profile";
    }
}
