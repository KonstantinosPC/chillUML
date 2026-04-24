package chill.guys.chillUML.controllers;

import chill.guys.chillUML.domain.Project;
import chill.guys.chillUML.repositories.ProjectRepository;
import chill.guys.chillUML.services.ProjectServicesImpl;
import chill.guys.chillUML.services.UserServicesImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class ProjectController {

    @Autowired
    UserServicesImpl userServices;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    ProjectServicesImpl projectServices;


    @GetMapping("/project/{name}")
    public String toProject(@PathVariable String name, @AuthenticationPrincipal UserDetails userDetails, Model model, RedirectAttributes redirectAttributes){
        Optional<Project> currentProject = projectRepository.findByProjectNameAndOwnerId(name, userServices.findByUsername(userDetails.getUsername()).get());
        if(!(currentProject.isEmpty())){
            model.addAttribute("project",currentProject.get());
            return "project";
        }else{
            redirectAttributes.addFlashAttribute("critical", "This project doesn't exists");
            return "redirect:/dashboard";
        }
    }
}
