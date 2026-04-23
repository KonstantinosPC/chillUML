package chill.guys.chillUML.services;


import chill.guys.chillUML.DTO.ProjectDTO;
import chill.guys.chillUML.domain.Project;
import chill.guys.chillUML.domain.User;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

public interface ProjectServices {

    public void createProject(ProjectDTO projectDTO, RedirectAttributes redirectAttributes);
    public List<Project> viewAllProjects(User ownerId);
    public void deleteProject(int project_id);
    public void editProjectName(int projectID,String newName, RedirectAttributes redirectAttributes);
    public void editProjectDescription(int projectID,String newDescription,RedirectAttributes redirectAttributes);
}
