package chill.guys.chillUML.controllers;

import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.services.UserServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    UserServicesImpl userServices;

    @GetMapping("/dashboard")
    public String toDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model){
        User currentUser = userServices.findByUsername(userDetails.getUsername()).orElse(null);
        model.addAttribute("user",currentUser);
        return "dashboard";
    }


}
