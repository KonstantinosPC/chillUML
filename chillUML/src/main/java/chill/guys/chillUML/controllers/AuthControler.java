package chill.guys.chillUML.controllers;


import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;

@Controller
public class AuthControler {

    @GetMapping("/auth/signup")
    public String showSignup(Model model){
        model.addAttribute("user", new User());
        return "signup";
    }



}
