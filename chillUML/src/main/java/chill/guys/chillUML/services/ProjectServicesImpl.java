package chill.guys.chillUML.services;

import chill.guys.chillUML.DTO.ProjectDTO;
import chill.guys.chillUML.domain.Project;
import chill.guys.chillUML.repositories.ProjectRepository;
import chill.guys.chillUML.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServicesImpl implements ProjectServices{
    String specialChars = "+-*/%=!<>&|^~(){}[];,.?:@_$";

    @Autowired
    private ProjectRepository projectRepository;

    @Override
    @Transactional
    public void createProject(ProjectDTO projectDTO, RedirectAttributes redirectAttributes) {
        if(projectDTO.getProjectName().length() > 15){
            redirectAttributes.addFlashAttribute("error","This project name is over 15 characters long");
            return;
        }
        if(!(projectRepository.findByIdWithProject(projectDTO.getProjectName(),projectDTO.getOwnerId()).isEmpty())){
            redirectAttributes.addFlashAttribute("error","There is already a project with this name");
            return;
        }
        if(!(projectDTO.getProjectName().chars().noneMatch(ch->specialChars.indexOf(ch) >= 0))){
            redirectAttributes.addFlashAttribute("error","The project name must not contain special characters.");
            return;
        }
        Project project = new Project();
        project.setProjectName(projectDTO.getProjectName());
        project.setProjectDescription(projectDTO.getProjectDescription());
        project.setOwner(projectDTO.getOwnerId());
        projectRepository.save(project);
        redirectAttributes.addFlashAttribute("success","The user " + project.getProjectName() + " has been created successfully");
    }

    @Override
    public List<Optional<Project>> viewAllProjects(int ownerId) {
        return projectRepository.findByOwner(ownerId);
    }

    @Override
    public void deleteProject(int projectId) {
        projectRepository.deleteById(projectId);
    }

    @Override
    public void editProjectName(int projectID,String newName,RedirectAttributes redirectAttributes) {
        Project project = projectRepository.findById(projectID).get();
        if(newName.length() > 15){
            redirectAttributes.addFlashAttribute("error","This project name is over 15 characters long");
            return;
        }
        if(!(projectRepository.findByIdWithProject(newName,project.getOwnerId())).isEmpty()){
            redirectAttributes.addFlashAttribute("error","There is already a project with this name");
            return;
        }
        if(!(newName.chars().noneMatch(ch->specialChars.indexOf(ch) >= 0))){
            redirectAttributes.addFlashAttribute("error","The project name must not contain special characters.");
            return;
        }
        project.setProjectName(newName);
        projectRepository.save(project);
    }

    @Override
    public void editProjectDescription(int projectID,String newDescription, RedirectAttributes redirectAttributes) {
        Project project = projectRepository.findById(projectID).get();
        project.setProjectDescription(newDescription);
    }
}
