package chill.guys.chillUML.controllers;

import chill.guys.chillUML.services.UserServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    UserServicesImpl userServices;

    @GetMapping("/dashboard")
    public String toDashboard(){
        return "dashboard";
    }


}
