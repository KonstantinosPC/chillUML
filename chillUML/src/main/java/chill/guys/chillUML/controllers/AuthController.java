package chill.guys.chillUML.controllers;


import chill.guys.chillUML.DTO.RegistrationForm;
import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.services.UserServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    UserServicesImpl userServices;

    @GetMapping("/auth/login")
    public String showLogin(){
        return "login";
    }

    @GetMapping("/auth/signup")
    public String showSignup(Model model){
        model.addAttribute("user", new User());
        return "signup";
    }


    @PostMapping("/auth/save")
    public String saveUser(@ModelAttribute("user")RegistrationForm form, Model model, RedirectAttributes redirectAttributes){
        Map<String, String> errors = new HashMap<>();
        userServices.saveUser(form, redirectAttributes);
        return "redirect:/auth/signup";
    }



}
