package chill.guys.chillUML.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProjectController {


    @GetMapping("/project")
    public String toProject(){
        return "dashboard";
    }
}
