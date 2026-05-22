package chill.guys.chillUML.controllers;

import chill.guys.chillUML.DTO.ProjectDTO;
import chill.guys.chillUML.domain.Project;
import chill.guys.chillUML.domain.User;
import chill.guys.chillUML.repositories.ProjectRepository;
import chill.guys.chillUML.services.ProjectServices;
import chill.guys.chillUML.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    UserServices userServices;

    @Autowired
    ProjectServices projectServices;

    @Autowired
    ProjectRepository projectRepository;

    @GetMapping("/")
    public String redirectToDashboard(){
        return "redirect:/dashboard";
    }

    @GetMapping("/dashboard")
    public String toDashboard(@AuthenticationPrincipal UserDetails userDetails, Model model){
        User currentUser = userServices.findByUsername(userDetails.getUsername()).orElse(null);
        List<Project> userProjects = projectServices.viewAllProjects(currentUser);
        model.addAttribute("user",currentUser);
        model.addAttribute("projects",userProjects);
        return "dashboard";
    }

    @PostMapping("/project/create-project")
    public String createProject(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("project-name")String projectName, @RequestParam("project-description")String projectDescription, RedirectAttributes redirectAttributes){
        ProjectDTO newProject = new ProjectDTO();
        newProject.setProjectName(projectName);
        newProject.setProjectDescription(projectDescription);
        newProject.setOwnerId(userServices.findByUsername(userDetails.getUsername()).get());
        projectServices.createProject(newProject,redirectAttributes);
        return "redirect:/dashboard";
    }

    @PostMapping("/project/edit-project")
    public String editProject(@AuthenticationPrincipal UserDetails userDetails, @RequestParam("edit-id")int id, @RequestParam("edit-name")String projectName, @RequestParam("edit-description")String projectDescription, RedirectAttributes redirectAttributes){
        if(!(projectRepository.findById(id).get().getProjectName().equals(projectName))){
            projectServices.editProjectName(id, projectName, redirectAttributes);
        }
        projectServices.editProjectDescription(id, projectDescription, redirectAttributes);
        return "redirect:/dashboard";
    }

    @PostMapping("/project/delete-project")
    public String deleteProject(@RequestParam("projectIds")List<Integer> projectsIds){
        for(Integer projectId: projectsIds){
            projectServices.deleteProject(projectId);
        }
        return "redirect:/dashboard";
    }
}
