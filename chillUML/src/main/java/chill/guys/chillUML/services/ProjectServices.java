package chill.guys.chillUML.services;
<<<<<<< Updated upstream
import chill.guys.chillUML.domain.Project;
import java.util.Optional;

import java.util.List;

public interface ProjectServices {
    List<Project> viewAllProjects(int userId);
    void createProject(String projectName);
    void editProject(String newProjectName);
    void deleteProject(String projectName);
    Optional <Project> findByName(String projectName);
=======

import chill.guys.chillUML.DTO.ProjectDTO;
import chill.guys.chillUML.domain.Project;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

public interface ProjectServices {

    public void createProject(ProjectDTO projectDTO, RedirectAttributes redirectAttributes);
    public List<Optional<Project>> viewAllProjects(int owner_id);
    public void deleteProject(int project_id);
    public void editProjectName(int projectID,String newName, RedirectAttributes redirectAttributes);
    public void editProjectDescription(int projectID,String newDescription,RedirectAttributes redirectAttributes);
>>>>>>> Stashed changes
}
