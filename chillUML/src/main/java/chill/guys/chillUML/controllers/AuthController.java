package chill.guys.chillUML.controllers;


import chill.guys.chillUML.domain.RegistrationForm;
import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class AuthController {

    @Autowired
    UserService userService;

    @GetMapping("/auth/login")
    public String showLogin(){
        return "login";
    }

    @GetMapping("/auth/signup")
    public String showSignup(Model model){
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/auth/login")
    public String login(@RequestParam("username")String username, @RequestParam("password")String password, Model model, RedirectAttributes redirectAttributes){
        Map<String, String> errors = new HashMap<>();
        System.out.println("-------------------------------------------------------------------------------------------------------->Username: " + username);
        System.out.println("Password: " + password);
        return "redirect:/auth/login";
    }

    @PostMapping("/auth/save")
    public String saveUser(@ModelAttribute("user")RegistrationForm form, Model model, RedirectAttributes redirectAttributes){
        Map<String, String> errors = new HashMap<>();
        System.out.println("Nigga here --------------------------------------------------------------------------------------------->" + form.getUsername());
        return "redirect:/auth/signup";
    }



}
