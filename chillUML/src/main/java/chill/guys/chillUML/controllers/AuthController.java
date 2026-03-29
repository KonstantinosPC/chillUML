package chill.guys.chillUML.controllers;


import chill.guys.chillUML.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.method.AuthorizeReturnObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    UserService userService;

    @GetMapping("/auth/login")
    public String showLogin(){
        return "login";
    }
    @GetMapping("/auth/signup")
    public String showSignup(){
        return "signup";
    }



}
